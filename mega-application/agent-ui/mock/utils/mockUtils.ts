import type {Request, Response} from "express";
// @ts-ignore
import captchapng from "captchapng3";


const waitTime = (time: number = 100) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(true);
    }, time);
  });
};

function guid () {
  return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
    const r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
}

async function getFakeCaptcha(req: Request, res: Response) {
  await waitTime(2000);
  return res.json('captcha-xxx');
}


async function getCaptchaImage(req: Request, res: Response) {
  await waitTime(1000);
  const rand = (Math.random() * 9000 + 1000).toFixed();
  const p = new captchapng(100, 30, rand);
  const img = p.getBase64();
  res.status(200).send({
    code: 200,
    msg: 'success',
    img: img,
    uuid: guid()
  });
}

export {
  waitTime,
  guid,
  getFakeCaptcha,
  getCaptchaImage,
};
