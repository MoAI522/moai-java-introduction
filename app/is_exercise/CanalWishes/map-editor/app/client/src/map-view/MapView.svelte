<script lang="ts">
  import {
    command,
    enemy_index,
    item_index,
    mapchip_index,
    mapchip_size,
    focused_enemy,
    focused_item,
    TCommand,
  } from "../stores/editor-config";
  import {
    enemies,
    map_height,
    items,
    tiles,
    map_width,
  } from "../stores/map-data";
  import TextureRenderer from "../TextureRenderer.svelte";
  import enemy_data from "../assets/enemy.json";
  import item_data from "../assets/item.json";
  import { v4 as uuid } from "uuid";
  import type { TEnemyData, TItemData } from "../types";

  $map_width = 26;
  $map_height = 20;
  $tiles = [...Array($map_height)].map(() =>
    [...Array($map_width)].map(() => 0)
  );

  let dragging = false;
  const onMouseDown = (x: number, y: number) => {
    if ($command != "BLOCK") return;
    dragging = true;
    onMouseEnter(x, y);
  };
  const onMouseUp = () => {
    dragging = false;
  };
  const onMouseEnter = (x: number, y: number) => {
    if (!dragging) return;
    $tiles[y][x] = $mapchip_index;
  };
  const onClick = (x: number, y: number) => {
    if ($command == "ENEMY") {
      if (
        $enemies.some((val) => val.position[0] == x && val.position[1] == y)
      ) {
        const id = $enemies.find(
          (val) => val.position[0] == x && val.position[1] == y
        ).id;
        $focused_enemy = id;
        return;
      }
      const id = uuid();
      $enemies = [
        ...$enemies,
        {
          id,
          class: enemy_data[$enemy_index].class,
          position: [x, y],
          type: 0,
          direction: "left",
          _index: $enemy_index,
        },
      ];
      $focused_enemy = id;
    } else if ($command == "ITEM") {
      if ($items.some((val) => val.position[0] == x && val.position[1] == y)) {
        const id = $items.find(
          (val) => val.position[0] == x && val.position[1] == y
        ).id;
        $focused_item = id;
        return;
      }
      const id = uuid();
      $items = [
        ...$items,
        {
          id,
          class: item_data[$item_index].class,
          position: [x, y],
          _index: $item_index,
        },
      ];
      $focused_item = id;
    }
  };

  let tiles_to_render;
  tiles.subscribe((_tiles) => {
    tiles_to_render = _tiles.reduce((acc, row, y) => {
      row.forEach((tile, x) =>
        acc.push({
          x,
          y,
          render_data: {
            index: tile,
            src: "resources/textures/0.png",
            u: ((tile - 1) % 10) * 24,
            v: Math.floor((tile - 1) / 10) * 24,
            tw: 24,
            th: 24,
            w: $mapchip_size,
            h: $mapchip_size,
          },
        })
      );
      return acc;
    }, []);
  });

  let enemies_to_render;
  const generateEnemiesToRender = (
    _enemies: Array<TEnemyData>,
    _focused_enemy: string
  ) => {
    enemies_to_render = _enemies.map((enemy) => {
      const data = enemy_data.find((val) => val.class === enemy.class);
      return {
        x: enemy.position[0] * $mapchip_size,
        y: enemy.position[1] * $mapchip_size,
        w: data.coordinates[enemy.type][2] * 2,
        h: data.coordinates[enemy.type][3] * 2,
        render_data: {
          index: enemy._index,
          src: data.texture_src,
          u: data.coordinates[enemy.type][0],
          v: data.coordinates[enemy.type][1],
          tw: data.coordinates[enemy.type][2],
          th: data.coordinates[enemy.type][3],
          w: data.coordinates[enemy.type][2] * 2,
          h: data.coordinates[enemy.type][3] * 2,
          active: enemy.id == _focused_enemy,
        },
        onClick: (e: MouseEvent) => {
          if ($command !== "ENEMY" && $command !== "LIST") return;
          $focused_item = null;
          if (e.ctrlKey) {
            $enemies = $enemies.filter((v) => v.id !== enemy.id);
            $focused_enemy = null;
            return;
          }

          if ($focused_enemy == enemy.id) return;
          $focused_enemy = enemy.id;
        },
      };
    });
  };
  enemies.subscribe((_enemies) =>
    generateEnemiesToRender(_enemies, $focused_enemy)
  );
  focused_enemy.subscribe((_focused_enemy) =>
    generateEnemiesToRender($enemies, _focused_enemy)
  );

  let items_to_render;
  const generateItemsToRender = (
    _items: Array<TItemData>,
    _focused_item: string
  ) => {
    items_to_render = _items.map((item) => {
      const data = item_data.find((val) => val.class === item.class);
      return {
        x: item.position[0] * $mapchip_size,
        y: item.position[1] * $mapchip_size,
        w: data.coordinate[2] * 2,
        h: data.coordinate[3] * 2,
        render_data: {
          index: item._index,
          src: data.texture_src,
          u: data.coordinate[0],
          v: data.coordinate[1],
          tw: data.coordinate[2],
          th: data.coordinate[3],
          w: data.coordinate[2] * 2,
          h: data.coordinate[3] * 2,
          active: item.id == _focused_item,
        },
        onClick: (e: MouseEvent) => {
          if ($command !== "ITEM" && $command !== "LIST") return;
          $focused_enemy = null;
          if (e.ctrlKey) {
            $items = $items.filter((v) => v.id !== item.id);
            $focused_item = null;
            return;
          }
          if ($focused_item == item.id) return;
          $focused_item = item.id;
        },
      };
    });
  };
  items.subscribe((_items) => generateItemsToRender(_items, $focused_item));
  focused_item.subscribe((_focused_item) =>
    generateItemsToRender($items, _focused_item)
  );
</script>

<div class="wrapper">
  <div
    class="inner"
    style="width:{$map_width * $mapchip_size}px;height:{$map_height *
      $mapchip_size}px;"
  >
    <div>
      {#each tiles_to_render as tile}
        <div
          class="cell"
          on:mousedown={() => onMouseDown(tile.x, tile.y)}
          on:mouseup={onMouseUp}
          on:mouseenter={() => onMouseEnter(tile.x, tile.y)}
          on:click={() => onClick(tile.x, tile.y)}
          style="left:{tile.x * $mapchip_size}px;top:{tile.y *
            $mapchip_size}px;width:{$mapchip_size}px;height:{$mapchip_size}px"
        >
          <TextureRenderer data={tile.render_data} />
        </div>
      {/each}
    </div>
    <div>
      {#each enemies_to_render as enemy}
        <div
          class="cell"
          style="left:{enemy.x}px;top:{enemy.y}px;width:{enemy.w}px;height:{enemy.h}px"
          on:click={enemy.onClick}
        >
          <TextureRenderer data={enemy.render_data} />
        </div>
      {/each}
    </div>
    <div>
      {#each items_to_render as item}
        <div
          class="cell"
          style="left:{item.x}px;top:{item.y}px;width:{item.w}px;height:{item.h}px"
          on:click={item.onClick}
        >
          <TextureRenderer data={item.render_data} />
        </div>
      {/each}
    </div>
  </div>
</div>

<style>
  .wrapper {
    position: relative;
    overflow: scroll;
    width: 100%;
    height: 100%;
  }

  .inner {
    background-color: blue;
    position: absolute;
    top: 0;
    left: 0;
  }

  .cell {
    position: absolute;
  }
</style>
