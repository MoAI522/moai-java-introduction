<script lang="ts">
  import {
    COMMANDS,
    command,
    focused_enemy,
    focused_fo,
  } from "../stores/editor-config";

  const commands = COMMANDS.map((cmd) => {
    let text = "";
    switch (cmd) {
      case "BLOCK":
        text = "ブロック配置";
        break;
      case "ENEMY":
        text = "敵配置";
        break;
      case "FIELD_OBJECT":
        text = "FO配置";
        break;
      case "LIST":
        text = "エンティティ管理";
        break;
      case "RESIZE":
        text = "リサイズ";
        break;
      case "FILES":
        text = "ファイル";
        break;
    }
    return { cmd, text };
  });
</script>

<div class="wrapper">
  {#each commands as cmd}
    {#if cmd.cmd == $command}
      <div class="cell active">{cmd.text}</div>
    {:else}
      <div
        class="cell"
        on:click={() => {
          $command = cmd.cmd;
          $focused_enemy = null;
          $focused_fo = null;
        }}
      >
        {cmd.text}
      </div>
    {/if}
  {/each}
</div>

<style lang="scss">
  .wrapper {
    display: flex;
    flex-direction: column;
  }

  .cell {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 30px;
    color: #000000;
    font-size: 18px;
    transition-duration: 0.1s;
    cursor: pointer;
    user-select: none;

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
