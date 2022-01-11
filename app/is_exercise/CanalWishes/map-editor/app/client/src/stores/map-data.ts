import { writable } from "svelte/store";
import type { TEnemyData, TFOData } from "../types";

export const map_width = writable(0);
export const map_height = writable(0);
export const tiles = writable([]);
export const enemies = writable<Array<TEnemyData>>([]);
export const field_objects = writable<Array<TFOData>>([]);
