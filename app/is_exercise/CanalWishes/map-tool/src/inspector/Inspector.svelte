<script lang="ts">
  import { focused_enemy, focused_item } from "../stores/editor-config";
  import { enemies, items, map_height, map_width } from "../stores/map-data";
  import enemy_data from "../assets/enemy.json";
  import item_data from "../assets/item.json";

  $: enemy =
    $focused_enemy != null
      ? $enemies.find((v) => v.id === $focused_enemy)
      : null;
  $: item =
    $focused_item != null ? $items.find((v) => v.id === $focused_item) : null;

  const onChange = () => {
    if (enemy != null) {
      if (
        enemy.type >=
        enemy_data.find((v) => v.class === enemy.class).coordinates.length
      ) {
        enemy.type =
          enemy_data.find((v) => v.class === enemy.class).coordinates.length -
          1;
      }

      const _enemies = $enemies.map((v) => {
        if (v.id === $focused_enemy) {
          return enemy;
        } else {
          return v;
        }
      });
      $enemies = _enemies;
    } else if (item != null) {
      const _items = $items.map((v) => {
        if (v.id === $focused_item) {
          return item;
        } else {
          return v;
        }
      });
    }
  };
</script>

<div class="wrapper">
  {#if enemy != null}
    <div class="ins-data">
      id:<input
        type="input"
        id="ins-enemy-id"
        size="1"
        bind:value={enemy.id}
        disabled
      />
    </div>
    <div class="ins-data">
      class:<select
        id="ins-enemy-class"
        bind:value={enemy.class}
        on:change={onChange}
      >
        {#each enemy_data as v}
          <option value={v.class}>{v.class.replace("com.moai.cw.", "")}</option>
        {/each}
      </select>
    </div>
    <div class="ins-data">
      type:<input
        type="number"
        id="ins-enemy-type"
        min="0"
        max={enemy_data.find((v) => v.class === enemy.class).coordinates
          .length - 1}
        bind:value={enemy.type}
        on:change={onChange}
      />
    </div>
    <div class="ins-data">
      position-x:<input
        type="number"
        id="ins-enemy-position-x"
        min="0"
        max={$map_width - 1}
        bind:value={enemy.position[0]}
        on:change={onChange}
      />
    </div>
    <div class="ins-data">
      position-y:<input
        type="number"
        id="ins-enemy-position-y"
        min="0"
        max={$map_height - 1}
        bind:value={enemy.position[1]}
        on:change={onChange}
      />
    </div>
    <div class="ins-data">
      direction:<select
        id="ins-enemy-direction"
        bind:value={enemy.direction}
        on:change={onChange}
        ><option value="left">left</option>
        <option value="right">right</option></select
      >
    </div>
  {:else if $focused_item != null}
    <div class="ins-data">
      id:<input
        type="input"
        id="ins-item-id"
        size="1"
        bind:value={item.id}
        disabled
      />
    </div>
    <div class="ins-data">
      class:<select
        id="ins-item-class"
        bind:value={item.class}
        on:change={onChange}
      >
        {#each item_data as v}
          <option value={v.class}>{v.class.replace("com.moai.cw.", "")}</option>
        {/each}
      </select>
    </div>
    <div class="ins-data">
      position-x:<input
        type="number"
        id="ins-item-position-x"
        min="0"
        max={$map_width - 1}
        bind:value={item.position[0]}
        on:change={onChange}
      />
    </div>
    <div class="ins-data">
      position-y:<input
        type="number"
        id="ins-item-position-y"
        min="0"
        max={$map_height - 1}
        bind:value={item.position[1]}
        on:change={onChange}
      />
    </div>
  {/if}
</div>

<style lang="scss">
  .wrapper {
    display: flex;
    flex-direction: column;
    padding: 20px;
    width: 300px;
    height: 100%;
  }

  .ins-data {
    font-size: 18px;
    display: flex;
    white-space: nowrap;

    &:not(:first-child) {
      margin-top: 10px;
    }

    & > input,
    & > select {
      flex: 1 1 auto;
      font-size: 18px;
      font-weight: bold;
      margin-left: 8px;
      overflow: hidden;
      width: 100%;
    }
  }
</style>
