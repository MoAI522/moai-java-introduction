<script lang="ts">
  import { fo_index } from "../stores/editor-config";

  import Picker from "./Picker.svelte";

  import fo_data from "../assets/fo.json";

  $: options = fo_data.map((fo, index) => ({
    render_data: {
      index: index,
      src: fo.texture_src,
      u: fo.coordinate[0],
      v: fo.coordinate[1],
      tw: fo.coordinate[2],
      th: fo.coordinate[3],
      w: fo.coordinate[2] * 2,
      h: fo.coordinate[3] * 2,
      active: $fo_index == index,
    },
    onClick: () => {
      $fo_index = index;
    },
  }));
</script>

<Picker {options} />
