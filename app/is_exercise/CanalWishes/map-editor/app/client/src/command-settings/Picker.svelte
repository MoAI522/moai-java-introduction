<script lang="ts">
  import TextureRenderer from "../TextureRenderer.svelte";
  import type { TTextureRenderData } from "../types";

  export let options: Array<{
    render_data: TTextureRenderData;
    onClick: () => void;
  }> = [];
</script>

<div class="wrapper">
  <div class="inner">
    {#each options as option}
      <div on:click={option.onClick}>
        <TextureRenderer data={option.render_data} />
      </div>
    {/each}
  </div>
</div>

<style lang="scss">
  .wrapper {
    overflow-x: hidden;
    overflow-y: scroll;
    height: 100%;
    width: 100%;
  }

  .inner {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
  }
</style>
