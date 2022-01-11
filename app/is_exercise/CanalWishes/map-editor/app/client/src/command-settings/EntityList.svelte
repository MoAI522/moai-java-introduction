<script lang="ts">
  import { focused_enemy, focused_fo } from "../stores/editor-config";

  import { enemies, field_objects } from "../stores/map-data";
</script>

<div class="pane">
  <div class="list">
    <div class="list-inner">
      {#each $enemies as enemy}
        <div
          class="cell {$focused_enemy == enemy.id && 'active'}"
          on:click={() => {
            $focused_enemy = enemy.id;
            $focused_fo = null;
          }}
        >
          <div class="cell-inner">[{enemy.class}]{enemy.id}</div>
        </div>
      {/each}
    </div>
  </div>
  <div class="list">
    <div class="list-inner">
      {#each $field_objects as fo}
        <div
          class="cell {$focused_fo == fo.id && 'active'}"
          on:click={() => {
            $focused_enemy = null;
            $focused_fo = fo.id;
          }}
        >
          <div class="cell-inner">[{fo.class}]{fo.id}</div>
        </div>
      {/each}
    </div>
  </div>
</div>

<style lang="scss">
  .pane {
    display: flex;
    flex-direction: row;
    width: 100%;
    height: 100%;
  }

  .list {
    width: 50%;
    height: 100%;
    position: relative;

    &:not(:first-child) {
      border-left: 1px solid #888888;
    }

    & > .list-inner {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      overflow-x: hidden;
      overflow-y: scroll;
    }
  }

  .cell {
    color: #000000;
    cursor: pointer;
    font-size: 14px;
    width: 100%;
    height: 24px;
    position: relative;

    & > .cell-inner {
      position: absolute;
      top: 0;
      left: 0;
      display: flex;
      align-items: center;
      width: 100%;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }

    &:not(:first-child) {
      border-top: 1px solid #aaaaaa;
    }

    &:hover {
      background: #cccccc;
    }

    &.active {
      background: #25599e;
      color: #ffffff;

      &:hover {
        background: #183a66;
      }
    }
  }
</style>
