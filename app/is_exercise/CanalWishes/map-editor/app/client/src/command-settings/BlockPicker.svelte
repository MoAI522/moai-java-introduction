<script lang="ts">
  import { mapchip_index } from "../stores/editor-config";

  import Picker from "./Picker.svelte";

  $: options = [...Array(101)].map((_, index) => ({
    render_data: {
      index: index,
      src: "resources/textures/0.png",
      u: ((index - 1) % 10) * 24,
      v: Math.floor((index - 1) / 10) * 24,
      tw: 24,
      th: 24,
      w: 48,
      h: 48,
      active: $mapchip_index == index,
    },
    onClick: () => {
      $mapchip_index = index;
    },
  }));
</script>

<Picker {options} />
