import { GlobalUserList } from '../model/GlobalUserList'
import { GlobalState } from '../model/GlobalState'
import { router } from '@kit.ArkUI'

@Entry
@Component
struct LoginView {
  @State username: string = ''
  @State password: string = ''

  // page B
  pageTransition() {
    // 定义页面进入时的效果，从下方侧滑入，时长为1000ms，无论页面栈发生push还是pop操作均可生效
    PageTransitionEnter({ type: RouteType.None, duration: 300 })
      .slide(SlideEffect.Bottom)
    // 定义页面退出时的效果，向下方侧滑出，时长为1200ms，无论页面栈发生push还是pop操作均可生效
    // PageTransitionExit({ type: RouteType.None, duration: 100 })
    //   .slide(SlideEffect.Bottom)
  }

  build() {
    Column({ space: 20 }) {
      //返回
      Image($r('app.media.return1'))
        .margin({right:'90%'})
        .height(20)
        .onClick(()=>{
          router.pushUrl({ url: 'pages/LoginPage' })
        })
      // 标题
      Text('登录')
        .fontSize(30)
        .fontWeight(FontWeight.Bold)
        .width('100%')
        .fontColor('#FFA500')
        .textAlign(TextAlign.Center)
        .margin({ bottom: 30 })

      // 用户名输入框
      TextInput({ placeholder: '请输入用户名', text: this.username })
        .height(50)
        .placeholderFont({size:20})
        .onChange((value: string) => {
          this.username = value
        })


      // 密码输入框
      TextInput({ placeholder: '请输入密码', text: this.password})
        .type(InputType.Password)
        .height(50)
        .placeholderFont({size:20})
        .onChange((value: string) => {
          this.password = value
        })

      // 登录按钮
      Button('登录')
        .width('80%')
        .height(50)
        .backgroundColor('#FFA500')
        .onClick(() => {
          const success = GlobalUserList.getInstance().login(this.username, this.password)
          if (success) {
            //找到用户并设置为当前用户
            const foundUser = GlobalUserList.getInstance()
              .getAllUsers()
              .find(u => u.username === this.username)

            if (foundUser) {
              //设置当前用户
              GlobalState.getInstance().setCurrentUser(foundUser)
              // 跳转首页
              router.pushUrl({ url: 'pages/Index' })
            }
          } else {
            AlertDialog.show({ message: '用户名或密码错误' })
          }
        })
    }
    .width('100%')
    .padding(20)
    .height('100%')
    // .justifyContent(FlexAlign.Center)
  }

}