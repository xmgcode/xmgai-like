import { getRandomElement, getRandomNumber } from "./utils.js";

const imageList = [
  "https://img1.baidu.com/it/u=1098124171,520637941&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1400/200x400",
  "https://img1.baidu.com/it/u=1098124171,520637941&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1400/300x500",
  "https://img1.baidu.com/it/u=1098124171,520637941&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1400/400x600",
  "https://img1.baidu.com/it/u=1098124171,520637941&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1400/500x700",
  "https://img1.baidu.com/it/u=1098124171,520637941&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1400/600x800",
  "https://img1.baidu.com/it/u=1098124171,520637941&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1400/700x900",
  "https://img1.baidu.com/it/u=1098124171,520637941&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1400/800x1000",
  "https://img1.baidu.com/it/u=1098124171,520637941&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1400/900x1100",
  "https://img1.baidu.com/it/u=1098124171,520637941&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1400/1000x1200",
  "https://img1.baidu.com/it/u=1098124171,520637941&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1400/1100x1300",
];

export const createList = (pageSize) => {
  let list = Array.from({ length: pageSize }, (v, i) => i);
  return list.map(x => {
    const width = getRandomNumber(200, 600);
    const height = getRandomNumber(400, 700);
    return {
      image: getRandomElement(imageList),
      width,
      height,
      x: 0,
      y: 0
    };
  });
}
