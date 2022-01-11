<script lang="ts">
  import { focused_enemy, focused_fo } from "../stores/editor-config";
  import {
    enemies,
    field_objects,
    map_height,
    map_width,
  } from "../stores/map-data";
  import enemy_data from "../assets/enemy.json";
  import fo_data from "../assets/fo.json";

  $: enemy =
    $focused_enemy != null
      ? $enemies.find((v) => v.id === $focused_enemy)
      : null;
  $: fo =
    $focused_fo != null
      ? $field_objects.find((v) => v.id === $focused_fo)
      : null;

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
    } else if (fo != null) {
      const _field_objects = $field_objects.map((v) => {
        if (v.id === $focused_fo) {
          return fo;
        } else {
          return v;
        }
      });
      $field_objects = _field_objects;
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
  {:else if $focused_fo != null}
    <div class="ins-data">
      id:<input
        type="input"
        id="ins-fo-id"
        size="1"
        bind:value={fo.id}
        disabled
      />
    </div>
    <div class="ins-data">
      class:<select
        id="ins-fo-class"
        bind:value={fo.class}
        on:change={onChange}
      >
        {#each fo_data as v}
          <option value={v.class}>{v.class.replace("com.moai.cw.", "")}</option>
        {/each}
      </select>
    </div>
    <div class="ins-data">
      position-x:<input
        type="number"
        id="ins-fo-position-x"
        min="0"
        max={$map_width - 1}
        bind:value={fo.position[0]}
        on:change={onChange}
      />
    </div>
    <div class="ins-data">
      position-y:<input
        type="number"
        id="ins-fo-position-y"
        min="0"
        max={$map_height - 1}
        bind:value={fo.position[1]}
        on:change={onChange}
      />
    </div>
    {#each [...Array(fo.params.length)].map((_, i) => i) as i}
      {#if fo_data.find((v) => v.class === fo.class).params[i].type === "string"}
        <div class="ins-data">
          {fo_data.find((v) => v.class === fo.class).params[i].name}:<input
            type="input"
            id={`ins-fo-param-${i}`}
            bind:value={fo.params[i]}
            on:change={onChange}
          />
        </div>
      {:else if fo_data.find((v) => v.class === fo.class).params[i].type === "number"}
        <div class="ins-data">
          {fo_data.find((v) => v.class === fo.class).params[i].name}:<input
            type="number"
            id={`ins-fo-param-${i}`}
            bind:value={fo.params[i]}
            on:change={onChange}
          />
        </div>
      {/if}
    {/each}
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
