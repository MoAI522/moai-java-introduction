import numpy as np
import xml.etree.ElementTree as et
import re

f = open("tusSymbol-opt.svg", "r")
svgData = f.read()
f.close()

root = et.fromstring(svgData)
xmlns = "http://www.w3.org/2000/svg"

operations = []
currentRad = 0
currentPos = np.array([0, 0])


def moveto(data):
    global currentRad, currentPos
    pos = np.array(
        list(map(float, data[0:2])))

    relPos = pos - currentPos
    norm = np.linalg.norm(relPos)
    rad = np.arccos(relPos[0] / norm) * \
        (-1 if relPos[1] < 0 else 1)
    relRad = rad - currentRad
    operations.append(["TURN", np.degrees(-relRad)])
    operations.append(["JUMP", norm])

    currentRad = rad
    currentPos = pos


def lineto(data):
    global currentRad, currentPos
    pos = np.array(
        list(map(float, data[0:2])))

    norm = np.linalg.norm(pos)
    rad = np.arccos(pos[0] / norm) * \
        (-1 if pos[1] < 0 else 1)
    relRad = rad - currentRad
    operations.append(["TURN", np.degrees(-relRad)])
    operations.append(["MOVE", norm])

    currentRad = rad
    currentPos += pos


def cubicBezier(data):
    global currentRad, currentPos
    c1 = np.array(list(map(float, data[0:2])))
    c2 = np.array(list(map(float, data[2:4])))
    pos = np.array(list(map(float, data[4:6])))

    norm = np.linalg.norm(pos)
    rad = np.arccos(pos[0] / norm) * \
        (-1 if pos[1] < 0 else 1)
    relRad = rad - currentRad
    rot = np.array([[np.cos(-rad), -np.sin(-rad)],
                    [np.sin(-rad), np.cos(-rad)]])
    relC1 = np.dot(rot, c1)
    relC2 = np.dot(rot, c2)
    operations.append(["TURN", np.degrees(-relRad)])
    operations.append(
        ["CBEZIER", relC1[0], relC1[1], relC2[0], relC2[1], norm])

    currentRad = rad
    currentPos += pos


def analyzePath(elem):
    global currentRad, currentPos
    tokens = re.findall(r"[mlcsz]|-?[\d\.]+(?:e-\d)?", elem.attrib["d"])

    commands = []

    def detectCommand(tokens):
        nonlocal commands
        command = [tokens[0]]
        for i in range(1, len(tokens)):
            if any([s == tokens[i] for s in "mlcsz"]):
                commands.append(command)
                detectCommand(tokens[i:])
                return

            command.append(tokens[i])

        commands.append(command)

    detectCommand(tokens)

    for command in commands:
        if (command[0] == "m"):
            moveto(command[1:3])
            for i in range(3, len(command), 2):
                lineto(command[i:i + 2])
        elif (command[0] == "l"):
            for i in range(1, len(command), 2):
                lineto(command[i:i + 2])
        elif (command[0] == "c"):
            for i in range(1, len(command), 6):
                if i + 6 <= len(command):
                    cubicBezier(command[i:i + 6])
                else:
                    lineto(command[i:i + 2])
        elif (command[0] == "s"):
            for i in range(1, len(command), 4):
                cubicBezier([0, 0, *command[i:i + 4]])
        elif (command[0] == "z"):
            pass


def analyzeGroup(group, _fill):
    global currentRad, currentPos
    for elem in group:
        fill = elem.attrib["fill"] != "none" if "fill" in elem.attrib else _fill
        if "stroke-width" in elem.attrib:
            operations.append(["SIZE", float(elem.attrib["stroke-width"])])

        if elem.tag == f"{{{xmlns}}}g":
            analyzeGroup(elem, fill)
        elif elem.tag == f"{{{xmlns}}}ellipse":
            pos = np.array([float(elem.attrib["cx"]),
                           float(elem.attrib["cy"])])
            radius = float(elem.attrib["rx"])

            relPos = pos - currentPos
            norm = np.linalg.norm(relPos)
            rad = np.arccos(relPos[0] / norm) * (-1 if relPos[1] < 0 else 1)
            relRad = rad - currentRad

            currentRad = rad
            currentPos = pos

            operations.append(["TURN", np.degrees(-relRad)])
            operations.append(["JUMP", norm])
            if fill:
                operations.append(["FCIRCLE", radius])
            else:
                operations.append(["CIRCLE", radius])
        elif elem.tag == f"{{{xmlns}}}path":
            analyzePath(elem)


analyzeGroup(root, True)

f = open("symbol.dat", "w")

for tokens in operations:
    line = ""
    for token in tokens:
        line += str(token) + " "
    line = line[:-1] + "\n"
    f.write(line)

f.close()
