<script lang="ts">
  import { onMount } from "svelte";
  import {
    enemies,
    field_objects,
    map_height,
    map_width,
    tiles,
  } from "../stores/map-data";
  import type { TEnemyData, TFOData } from "../types";
  import fo_data from "../assets/fo.json";

  let fileList = [];
  let selectedFilename: string = null;

  type TFile = {
    filename: string;
  };

  type TSaveData = {
    mapSize: Array<number>;
    tiles: Array<Array<number>>;
    enemies: Array<TEnemyData>;
    field_objects: Array<TFOData>;
  };

  const updateFileList = async () => {
    const res = await fetch("/list");
    fileList = (await res.json()) as Array<TFile>;
  };

  const load = async () => {
    const data = (await (
      await fetch(`/load/${selectedFilename}`)
    ).json()) as TSaveData;
    $map_width = data.mapSize[0];
    $map_height = data.mapSize[1];
    $tiles = data.tiles;
    $enemies = data.enemies;
    $field_objects = data.field_objects.map((fo) => {
      fo.params = fo.params.map((param, i) => {
        if (
          fo_data.find((v) => v.class === fo.class).params[i].type === "number"
        ) {
          return parseInt(param as string);
        } else {
          return param;
        }
      });
      return fo;
    });
  };

  const save = async (putName = false) => {
    let filename = selectedFilename;
    if (filename === null || putName) {
      filename = window.prompt(
        "ファイル名を入力してください。",
        filename || ""
      );
    }
    await fetch(`/save/${filename}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        mapSize: [$map_width, $map_height],
        tiles: $tiles,
        enemies: $enemies,
        field_objects: $field_objects.map((fo) => {
          fo.params = fo.params.map((param) => {
            if (typeof param === "number") {
              return param.toString();
            } else {
              return param;
            }
          });
          return fo;
        }),
      }),
    });
  };

  onMount(() => {
    updateFileList();
  });
</script>

<div class="wrapper">
  <div class="file-list">
    <select bind:value={selectedFilename} size="2">
      {#each fileList as file}
        <option value={file.filename}>{file.filename}</option>
      {/each}
    </select>
  </div>
  <div class="control">
    <button on:click={() => load()}>読み込み</button>
    <button on:click={() => save()}>保存</button>
    <button on:click={() => save(true)}>名前を付けて保存</button>
  </div>
</div>

<style lang="scss">
  .wrapper {
    display: flex;
    width: 100%;
    height: 100%;
  }

  .file-list {
    width: 50%;
    height: 100%;

    & > select {
      width: 100%;
      height: 100%;
    }
  }

  .control {
    flex: 1 1 auto;
    display: flex;
    flex-direction: column;
    height: 100%;
    padding: 20px;
  }
</style>
