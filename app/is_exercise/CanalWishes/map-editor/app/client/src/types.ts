export type TTextureRenderData = {
  index: number;
  src: string;
  u: number;
  v: number;
  tw: number;
  th: number;
  w: number;
  h: number;
  active: boolean;
};

export type TEnemyData = {
  id: string;
  class: string;
  type: number;
  position: Array<number>;
  direction: "left" | "right";
  _index: number;
};

export type TFOData = {
  id: string;
  class: string;
  position: Array<number>;
  params: Array<string | number>;
  _index: number;
};
