import { router, window } from '@kit.ArkUI'
import { GlobalState } from '../model/GlobalState'

@Entry
@Component
struct LoginPage {
  @State isToggled: boolean = false
  @State isBackA: boolean = true;
  //占满全屏
  aboutToAppear(): void {
    window.getLastWindow(getContext()).then((win)=>{
      // 2. 指定页面开启
      win.setWindowLayoutFullScreen(true)
    })
  }

  pageTransition() {
    if(this.isBackA){
      PageTransitionEnter({ type: RouteType.None, duration: 0 })
      PageTransitionExit({ type: RouteType.None, duration: 0 })
    }
  }

  build() {
    Column(){
      Row(){
        Text('随便看看>')
          .fontSize(20)
          .margin({top:25,left:'70%'})
          .fontColor('#ff7a7a7a')
          .fontWeight(FontWeight.Bold)
          .onClick(()=>{
            router.pushUrl({ url: 'pages/Index' }); // 跳转到 LearnPage 页面
          })
      }
      .width('100%')
      .height('10%')
      // .backgroundColor(Color.White)

      //Logo
      Row(){
        Column(){
          Column(){
            Button({ type: ButtonType.Normal }) {
              Text('不')
                .fontSize(50)
                .fontWeight(FontWeight.Bold)
                .fontColor('#FFA500')
            }
            .width(80)
            .height(80)
            .border({
              color: '#FFA500',
              width: 5,
              radius: '50%'
            })
            .margin({bottom:10,top:20})
            .backgroundColor('rgba(0,0,0,0)')

            Button({ type: ButtonType.Normal }) {
              Text('背')
                .fontSize(50)
                .fontWeight(FontWeight.Bold)
                .fontColor(Color.White)
            }
            .width(80)
            .height(80)
            .border({
              color: '#FFA500',
              width: 5,
              radius: '50%'
            })
            .backgroundColor('#FFA500')
          }

          Row(){
            Text('真实语境学单词')
              .fontSize(30)
              .fontWeight(FontWeight.Bold)
          }
          .width('100%')
          .height('35%')
          .justifyContent(FlexAlign.Center)
          // .backgroundColor(Color.Red)
        }
        .width('60%')
        .margin({bottom:'50%'})
        .justifyContent(FlexAlign.Center)
        // .backgroundColor(Color.Pink)

      }
      .width('100%')
      .height('50%')
      .margin({top:'5%'})
      .justifyContent(FlexAlign.Center)
      // .backgroundColor(Color.White)

      //登录按钮
      Column({space:20}){
        Button({ type: ButtonType.Normal }) {
          Text('登录')
            .fontSize(30)
            .fontWeight(FontWeight.Bold)
            .fontColor(Color.White)
        }
        .width('70%')
        .height(60)
        .borderRadius(10)
        .backgroundColor('#FFA500')
        .onClick(()=>{
          if(this.isToggled){
            GlobalState.getInstance().setLoginStatus(true)
            router.pushUrl({ url: 'pages/LoginVIewPage' })
          }
          else
            AlertDialog.show({ message: '请同意用户《服务条款》和《隐私协议》' })
        })

        Button({ type: ButtonType.Normal }) {
          Text('注册')
            .fontSize(30)
            .fontWeight(FontWeight.Bold)
            .fontColor('#FFA500')
        }
        .width('70%')
        .height(60)
        .borderRadius(10)
        .border({color:'#FFA500',width:2,radius:10})
        .backgroundColor(Color.White)
        .onClick(()=>{
          if(this.isToggled){
            GlobalState.getInstance().setLoginStatus(true)
            router.pushUrl({ url: 'pages/RegisterPage'})
          }
          else
            AlertDialog.show({ message: '请同意用户《服务条款》和《隐私协议》' })
        })

      }
      .width('100%')
      .height('20%')
      .justifyContent(FlexAlign.Center)
      // .backgroundColor(Color.Blue)

      Row({space:10}){
        Button() {
          Text('√')
            .fontSize(10)
            .fontWeight(FontWeight.Bold)
            .fontColor(this.isToggled ? Color.White : '#FFA500') // 选中白字，未选中橘字
        }
        .width(20)
        .height(20)
        .border({
          width: 1,
          color: '#FFA500'
        })
        .backgroundColor(this.isToggled ? '#FFA500' : Color.White) // 选中橘底，未选中白底
        .borderRadius(10)
        .onClick(() => {
          this.isToggled = !this.isToggled
        })

        Row(){
          Text('我已阅读并同意')
            .fontSize(15)
            .fontColor('#ff7a7a7a')
            .fontWeight(FontWeight.Bold)
          Text('《服务条款》')
            .fontSize(15)
            .fontColor('#FFA500')
            .fontWeight(FontWeight.Bold)
          Text('和')
            .fontSize(15)
            .fontColor('#ff7a7a7a')
            .fontWeight(FontWeight.Bold)
          Text('《隐私协议》')
            .fontSize(15)
            .fontColor('#FFA500')
            .fontWeight(FontWeight.Bold)
        }

      }
      .width('100%')
      .height('10%')
      .margin({top:'15%'})
      .justifyContent(FlexAlign.Center)
      // .backgroundColor(Color.Red)

    }
    .width('100%')
    .height('100%')
    .backgroundImage($r('app.media.background'))
    .backgroundImageSize(1)
    // .backgroundColor(Color.Pink)
  }
}