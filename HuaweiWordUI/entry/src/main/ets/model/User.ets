// model/User.ets
import router from '@ohos.router';
import image from '@ohos.multimedia.image';  // 正确导入 image 模块 [[6]](https://developer.huawei.com/consumer/cn/doc/documentation/doc-references-v5/modifying-code-during-hot-reload-is-not-supported-0000001365204884-v5)

export class User {
  username: string
  password: string
  avatarUrl: Resource | image.PixelMap  // 使用 PixelMap 显示本地或网络图片 [[9]](https://developer.huawei.com/consumer/cn/doc/documentation/doc-references-v5/modifying-code-during-hot-reload-is-not-supported-0000001365204884-v5)

  constructor(username: string, password: string, avatarUrl: Resource | image.PixelMap) {
    this.username = username
    this.password = password
    this.avatarUrl = avatarUrl
  }

  public setName(name: string): void {
    this.username = name;
  }

  public getName():string{
    return this.username
  }

  public setPassword(password:string) :void{
    this.password=password
  }

  public setAvatarUrl(avatarurl:Resource | image.PixelMap) :void{
    this.avatarUrl=avatarurl
  }
}