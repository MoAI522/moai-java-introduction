public class ListRev {
  static ListNode listRev(ListNode head) {
    ListNode currentNode = head;
    ListNode nextNode = currentNode.next;
    currentNode.next = null;
    while (nextNode != null) {
      ListNode temp = nextNode.next;
      nextNode.next = currentNode;
      currentNode = nextNode;
      nextNode = temp;
    }
    return currentNode;
  }

  static ListNode listRev2(ListNode head) {
    if (head.next == null) {
      return head;
    }
    ListNode tail = listRev2(head.next);
    head.next.next = head;
    head.next = null;
    return tail;
  }

  static ListNode generateNodeFromArray(String[] array, int index) {
    if (index == array.length)
      return null;
    ListNode head = new ListNode(array[index]);
    head.next = generateNodeFromArray(array, index + 1);
    return head;
  }

  static boolean evalNodes(ListNode a, ListNode b) {
    if (a.next == null && b.next == null)
      return true;
    else if (a.next == null ^ b.next == null)
      return false;
    return a.word.equals(b.word) && evalNodes(a.next, b.next);
  }

  static String nodeToString(ListNode node) {
    if (node.next == null) {
      return node.word;
    }
    return node.word + "," + nodeToString(node.next);
  }

  public static void main(String[] args) {
    String[] str1 = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
    String[] str2 = { "9", "8", "7", "6", "5", "4", "3", "2", "1", "0" };
    ListNode result1 = listRev(generateNodeFromArray(str1, 0));
    ListNode result2 = listRev2(generateNodeFromArray(str1, 0));
    ListNode dest = generateNodeFromArray(str2, 0);

    System.out.println(
        "listRev(No Recursion)\t| eval:" + evalNodes(dest, result1) + "  detail:[" + nodeToString(result1) + "]");
    System.out.println(
        "listRev2(Recursion)\t| eval:" + evalNodes(dest, result2) + "  detail:[" + nodeToString(result2) + "]");
  }
}

class ListNode {
  ListNode next;
  String word;

  ListNode(String s) {
    next = null;
    word = s;
  }
}
