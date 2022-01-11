import { writable } from "svelte/store";

type TCommands = ["BLOCK", "ENEMY", "FIELD_OBJECT", "LIST", "RESIZE", "FILES"];
export const COMMANDS: TCommands = [
  "BLOCK",
  "ENEMY",
  "FIELD_OBJECT",
  "LIST",
  "RESIZE",
  "FILES",
];
export type TCommand = TCommands[number];

export const mapchip_size = writable(48);
export const mapchip_index = writable(1);
export const enemy_index = writable(0);
export const fo_index = writable(0);
export const command = writable<TCommand>("BLOCK");
export const focused_enemy = writable(null);
export const focused_fo = writable(null);
