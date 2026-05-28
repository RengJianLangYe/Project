import { GlobalUserList } from '../model/GlobalUserList'
import { GlobalState } from '../model/GlobalState'
import { router } from '@kit.ArkUI'
import { User } from '../model/User'


@Entry
@Component
struct RegisterPage {
  @State username: string = ''
  @State password: string = ''
  @State confirmPassword: string = ''

  // 页面动画（可选）
  pageTransition() {
    PageTransitionEnter({ type: RouteType.None, duration: 300 })
      .slide(SlideEffect.Bottom)
  }

  build() {
    Column({ space: 20 }) {
      // 返回按钮
      Image($r('app.media.return1'))
        .width(20)
        .height(20)
        .margin({ right: '90%' })
        .onClick(()=>{
          router.pushUrl({ url: 'pages/LoginPage' })
        })


      // 标题
      Text('注册')
        .fontSize(30)
        .fontWeight(FontWeight.Bold)
        .width('100%')
        .fontColor('#FFA500')
        .textAlign(TextAlign.Center)
        .margin({ bottom: 30 })

      // 用户名输入框
      TextInput({ placeholder: '请输入用户名', text: this.username })
        .height(50)
        .placeholderFont({ size: 20 })
        .onChange((value: string) => {
          this.username = value
        })

      // 密码输入框
      TextInput({ placeholder: '请输入密码', text: this.password })
        .type(InputType.Password)
        .height(50)
        .placeholderFont({ size: 20 })
        .onChange((value: string) => {
          this.password = value
        })

      // 确认密码输入框
      TextInput({ placeholder: '请确认密码', text: this.confirmPassword })
        .type(InputType.Password)
        .height(50)
        .placeholderFont({ size: 20 })
        .onChange((value: string) => {
          this.confirmPassword = value
        })

      // 注册按钮
      Button('注册')
        .width('80%')
        .height(50)
        .backgroundColor('#FFA500')
        .onClick(() => {
          if (!this.username || !this.password || !this.confirmPassword) {
            AlertDialog.show({ message: '所有字段都不能为空' })
            return
          }

          if (this.password !== this.confirmPassword) {
            AlertDialog.show({ message: '两次输入的密码不一致' })
            return
          }

          const user = new User(this.username, this.password, $r('app.media.head'))
          GlobalUserList.getInstance().addUser(user)  //添加用户
          GlobalState.getInstance().setCurrentUser(user)  //设置当前用户
          AlertDialog.show({ message: '注册成功' })
          router.pushUrl({ url: 'pages/Index' })
        })
    }
    .width('100%')
    .padding(20)
    .height('100%')

  }
}