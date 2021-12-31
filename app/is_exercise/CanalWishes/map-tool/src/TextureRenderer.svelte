<script lang="ts">
  import type { TTextureRenderData } from "./types";

  export let data: TTextureRenderData;
</script>

<div class="wrapper" style="width:{data.w}px;height:{data.h}px;">
  <img
    src={data.src}
    alt={data.index.toString()}
    style="left:{-data.u * 2}px;top:{-data.v * 2}px"
  />
  <span class="index">{data.index}</span>
  {#if data.active}
    <div class="focus" />
  {/if}
</div>

<style lang="scss">
  .wrapper {
    position: relative;
    overflow: hidden;
    color: #ffffff;
    padding: 10px;
    user-select: none;

    & > img {
      width: 512px;
      height: 512px;
      position: absolute;
      user-drag: none;
      -webkit-user-drag: none;
      -moz-user-select: none;
    }

    & > .index {
      color: #ffffff;
      font-size: 14px;
      font-weight: bold;
      background: rgba(0, 0, 0, 0.3);
      position: relative;
    }

    & > .focus {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      border: 2px solid #e96958;
    }
  }
</style>
