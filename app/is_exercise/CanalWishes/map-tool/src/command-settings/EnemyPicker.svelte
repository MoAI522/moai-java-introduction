<script lang="ts">
  import { enemy_index } from "../stores/editor-config";

  import Picker from "./Picker.svelte";

  import enemy_data from "../assets/enemy.json";

  $: options = enemy_data.map((enemy, index) => ({
    render_data: {
      index: index,
      src: enemy.texture_src,
      u: enemy.coordinates[0][0],
      v: enemy.coordinates[0][1],
      tw: enemy.coordinates[0][2],
      th: enemy.coordinates[0][3],
      w: enemy.coordinates[0][2] * 2,
      h: enemy.coordinates[0][3] * 2,
      active: $enemy_index == index,
    },
    onClick: () => {
      $enemy_index = index;
    },
  }));
</script>

<Picker {options} />
