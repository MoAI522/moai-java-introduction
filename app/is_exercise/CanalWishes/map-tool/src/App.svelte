<script lang="ts">
  import CommandList from "./command-list/CommandList.svelte";
  import CommandSettings from "./command-settings/CommandSettings.svelte";
  import MapView from "./map-view/MapView.svelte";
</script>

<div class="pane1">
  <div class="pane2">
    <div class="map-view-wrapper wrapper">
      <MapView />
    </div>
    <div class="pane3">
      <div class="command-list-wrapper wrapper"><CommandList /></div>
      <div class="command-settings-wrapper wrapper"><CommandSettings /></div>
    </div>
  </div>
  <div class="inspector-wrapper wrapper">inspector</div>
</div>

<style>
  .pane1 {
    display: flex;
    flex-direction: row;
    width: 100%;
    height: 100%;
    /* overflow: hidden; */
  }

  .pane2 {
    flex: 1 1 auto;
    display: flex;
    flex-direction: column;
    /* overflow: hidden; */
  }

  .pane3 {
    flex: 0 0 400px;
    display: flex;
    flex-direction: row;
    /* overflow: hidden; */
  }

  .wrapper {
    border: 2px solid gray;
  }

  .map-view-wrapper {
    flex: 1 1 auto;
  }

  .inspector-wrapper {
    flex: 0 0 300px;
  }

  .command-list-wrapper {
    flex: 0 0 200px;
  }

  .command-settings-wrapper {
    flex: 1 1 auto;
  }
</style>
