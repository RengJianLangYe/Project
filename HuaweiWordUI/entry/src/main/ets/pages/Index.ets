import { CustomButton } from '../components/CustomButton'
import { GlobalState } from '../model/GlobalState'
import { GlobalUserList } from '../model/GlobalUserList'
import { User } from '../model/User'

//路由管理模块
import router from '@ohos.router';

import { window } from '@kit.ArkUI';

@Entry
@Component
struct Index {
  @State buttonText: string = '点击签到'
  @State bgColor: string = 'rgba(255, 255, 255, 0.7)'
  @State isBackA: boolean = true;

  @State currentUser: User | null = null // 当前用户状态
  //占满全屏
  aboutToAppear(): void {
    window.getLastWindow(getContext()).then((win)=>{
      // 2. 指定页面开启
      win.setWindowLayoutFullScreen(true)
    })

    // 从全局状态获取当前用户
    const user = GlobalState.getInstance().getCurrentUser()
    if (user) {
      this.currentUser = user
    }
  }

  pageTransition() {
    if(this.isBackA){
      PageTransitionEnter({ type: RouteType.None, duration: 0 })
      PageTransitionExit({ type: RouteType.None, duration: 0 })
    }
  }


  build() {
    Column() {
      // // 背景图片
      // Image($r('app.media.background1'))
      //   .width('100%')
      //   .height('100%')
      //   .objectFit(ImageFit.Cover)
        //头像按钮
        Column() {
          Image(this.currentUser?.avatarUrl ?? $r('app.media.head'))
            .width(40)
            .height(40)
            .borderRadius(50)
            .margin({ left: '5%' })
            .onClick(()=>{
              router.pushUrl({ url: 'pages/SettingPage' }); // 跳转到 LearnPage 页面
            })

        }
        .width('100%')
        .height('10%')
        .margin({top:'10%'})
        .alignItems(HorizontalAlign.Start)


        // 上半部分：用于占位，使下面的内容居中
        Blank()
          .layoutWeight(0.5)

        // 居中区域：签到按钮
        Column({ space: 20 }) {
          CustomButton({
            label: this.buttonText,
            btnWidth: '50%',
            btnHeight: '100%',
            btnFontSize: 35,
            btnBackgroundColor: this.bgColor,
            onPress: () => {
              if(GlobalState.getInstance().isLoggedIn()){
                this.buttonText = '签到成功'
                this.bgColor = 'rgba(255, 255, 255, 0)'
              }else {
                AlertDialog.show({ message: '请先登录' })
              }

            }
          })
        }
        .width('100%')
        .height('23%')
        .alignItems(HorizontalAlign.Center)


        // 上半部分：用于占位，使下面的内容居中
        Blank()
          .layoutWeight(1)



        // 下半部分：底部Learn Review按钮区域
        Row({ space: 20 }) {
          CustomButton({
            label: 'Learn',
            btnWidth: '45%',
            btnHeight: '100%',
            btnFontSize: 25,
            btnBackgroundColor: 'rgba(255, 255, 255, 0.7)',
            fontWeight:FontWeight.Bold,
            onPress: () => {
              if(GlobalState.getInstance().isLoggedIn()){
                router.pushUrl({ url: 'pages/LearnPage' }); // 跳转到 LearnPage 页面
              }else{
                AlertDialog.show({ message: '请先登录' })

              }
            }
          })

          CustomButton({
            label: 'Review',
            btnWidth: '45%',
            btnHeight: '100%',
            btnFontSize: 25,
            btnBackgroundColor: 'rgba(255, 255, 255, 0.7)',
            fontWeight:FontWeight.Bold,
            onPress: () => {
              if(GlobalState.getInstance().isLoggedIn()){
                router.pushUrl({ url: 'pages/ReviewPage' }); // 跳转到 ReviewPage 页面
              }else{
                AlertDialog.show({ message: '请先登录' })

              }
            }
          })

        }
        // .backgroundColor(Color.Red)
        .width('100%')
        .height('13%')
        .justifyContent(FlexAlign.Center)
        // .padding({ bottom: '20%' }) // 底部留白，适配安全区域
        .margin({bottom:'4%'})

      //底部导航栏
      Row({ space: 15 }){
        Image($r('app.media.cap'))
          .width('10%')
          .onClick(()=>{
            // router.pushUrl({ url: 'pages/Index' }); // 跳转到 IndexPage 页面
          })

        Image($r('app.media.file'))
          .width('10%')
          .onClick(()=>{
            router.pushUrl({ url: 'pages/FilePage' }); // 跳转到 IndexPage 页面
          })

        Image($r('app.media.line'))
          .width('10%')
          .onClick(()=>{
            router.pushUrl({ url: 'pages/Index' }); // 跳转到 IndexPage 页面
          })

      }
      .width('90%')
      .height('8%')
      .justifyContent(FlexAlign.SpaceBetween)
      // .justifyContent(FlexAlign.Center)
      // .backgroundColor('rgba(255, 255, 255, 0.7)')
    }
    .backgroundImage($r('app.media.background1'))
    .backgroundImageSize(1)
    // .backdropBlur(30)
    .width('100%')
    .height('100%')
  }
}