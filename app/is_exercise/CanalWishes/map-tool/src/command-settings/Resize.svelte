<script lang="ts">
  import {
    enemies,
    items,
    map_height,
    map_width,
    tiles,
  } from "../stores/map-data";

  let w = $map_width;
  let h = $map_height;
  let offset_x = 0;
  let offset_y = 0;

  const onClick = () => {
    $tiles = [...Array(h)].map((_, y) =>
      [...Array(w)].map((_, x) => {
        if (
          x - offset_x >= 0 &&
          x - offset_x < $map_width &&
          y - offset_y >= 0 &&
          y - offset_y < $map_height
        ) {
          return $tiles[y - offset_y][x - offset_x];
        } else {
          return 0;
        }
      })
    );
    $enemies = $enemies
      .map((enemy) => ({
        ...enemy,
        position: [enemy.position[0] + offset_x, enemy.position[1] + offset_y],
      }))
      .filter(
        (enemy) =>
          enemy.position[0] >= 0 &&
          enemy.position[0] < w &&
          enemy.position[1] >= 0 &&
          enemy.position[1] < h
      );
    $items = $items
      .map((item) => ({
        ...item,
        position: [item.position[0] + offset_x, item.position[1] + offset_y],
      }))
      .filter(
        (item) =>
          item.position[0] >= 0 &&
          item.position[0] < w &&
          item.position[1] >= 0 &&
          item.position[1] < h
      );
    $map_width = w;
    $map_height = h;
  };
</script>

<div class="wrapper">
  <div>width:<input type="number" bind:value={w} /></div>
  <div>
    height:
    <input type="number" bind:value={h} />
  </div>
  <div>
    offset_x:
    <input type="number" bind:value={offset_x} />
  </div>
  <div>
    offset_y:
    <input type="number" bind:value={offset_y} />
  </div>

  <button type="button" on:click={onClick}>リサイズ</button>
</div>

<style lang="scss">
  .wrapper {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
  }
</style>
