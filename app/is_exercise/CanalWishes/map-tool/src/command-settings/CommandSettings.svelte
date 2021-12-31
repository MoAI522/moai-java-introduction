<script lang="ts">
  import { command } from "../stores/editor-config";
  import BlockPicker from "./BlockPicker.svelte";
  import EnemyPicker from "./EnemyPicker.svelte";
  import EntityList from "./EntityList.svelte";
  import Files from "./Files.svelte";
  import ItemPicker from "./ItemPicker.svelte";
  import Resize from "./Resize.svelte";
</script>

{#if $command == "BLOCK"}
  <BlockPicker />
{:else if $command == "ENEMY"}
  <EnemyPicker />
{:else if $command == "ITEM"}
  <ItemPicker />
{:else if $command == "LIST"}
  <EntityList />
{:else if $command == "RESIZE"}
  <Resize />
{:else if $command == "FILES"}
  <Files />
{/if}
