<script lang="ts">
  import { item_index } from "../stores/editor-config";

  import Picker from "./Picker.svelte";

  import item_data from "../assets/item.json";

  $: options = item_data.map((enemy, index) => ({
    render_data: {
      index: index,
      src: enemy.texture_src,
      u: enemy.coordinate[0],
      v: enemy.coordinate[1],
      tw: enemy.coordinate[2],
      th: enemy.coordinate[3],
      w: enemy.coordinate[2] * 2,
      h: enemy.coordinate[3] * 2,
      active: $item_index == index,
    },
    onClick: () => {
      $item_index = index;
    },
  }));
</script>

<Picker {options} />
