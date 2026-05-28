import { CustomReviewCard } from '../components/CustomReviewCard'
import {CustomBookInfo } from '../components/CustomBookInfo'

//路由管理模块
import router from '@ohos.router';

import { window } from '@kit.ArkUI';

@Entry
@Component
struct FilePage {

  //占满全屏
  aboutToAppear(): void {
    window.getLastWindow(getContext()).then((win)=>{
      // 2. 指定页面开启
      win.setWindowLayoutFullScreen(true)
    })
  }

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
    Column(){
      Row(){
        //返回的箭头（记得改成向下的箭头
        Image($r('app.media.return1'))
          .width('8%')
          .margin({left:'6%'})
          .onClick(()=>{
            router.pushUrl({ url: 'pages/Index' }); // 跳转到 IndexPage 页面
          })
        Text('我的内容')
          .fontSize(20)
          .fontColor('#000000')
          .fontWeight(FontWeight.Bold)
          .textAlign(TextAlign.Center)
          // .margin({right:'23%'})
        Image($r('app.media.light'))
          .width('10%')
      }
      .width('100%')
      .height('8%')
      .justifyContent(FlexAlign.SpaceBetween)
      // .backgroundColor(Color.White)

      //上方滑动区域
      Scroll() {
        // 只能有一个子组件
        Row() {
          // 橘框
          Column() {
            //随身听
            Row(){
              Image($r("app.media.ear1"))
                .width('13%')
                .borderRadius(50)
                .backgroundColor(Color.Orange)
              Text('随身听')
                .fontSize(25)
                .fontWeight(FontWeight.Bold)
                .fontColor(Color.Orange)
                .fontWeight(FontWeight.Bold)
                // .textAlign(TextAlign.Center)
                .margin({left:'3%'})
            }
            .width('85%')
            .height('20%')
            .margin({top:'2%'})
            // .backgroundColor(Color.White)

            Row(){
              //回顾框
              CustomReviewCard({
                BackgroundColor: '#e2fffbfb',
                iconResourceId: $r('app.media.play'),
                title: '回顾',
                wordCount: '20词'
              })
              //预习框
              CustomReviewCard({
                BackgroundColor: '#e2fffbfb',
                iconResourceId: $r('app.media.play'),
                title: '预习',
                wordCount: '20词'
              })
            }
            .width('85%')
            .height('65%')
            .justifyContent(FlexAlign.SpaceBetween)
          }
          .backgroundColor('#6dffa76d')
          .width(280)
          .height(200)
          .margin({left:25})
          .borderRadius(20)

          //蓝框
          Column() {
            //听写
            Row(){
              Image($r("app.media.write"))
                .width('13%')
                .margin({left:15})
                .borderRadius(50)
                .backgroundColor('#ff35b5e9')
              Text('听写')
                .fontSize(25)
                .fontWeight(FontWeight.Bold)
                .fontColor('#ff35b5e9')
                .fontWeight(FontWeight.Bold)
                  // .textAlign(TextAlign.Center)
                .margin({left:'3%'})
            }
            .width('85%')
            .height('20%')
            .margin({top:'2%'})
            // .backgroundColor(Color.White)

            Row(){
              CustomReviewCard({
                BackgroundColor: '#e2fffbfb',
                iconResourceId: $r('app.media.play1'),
                title: '随堂测',
                wordCount: '20词'
              })
            }
            .margin({left:120})
            .width('130%')
            .height('65%')
            // .justifyContent(FlexAlign.SpaceBetween)
          }
          .backgroundColor('#4f64c6ee')
          .width(180)
          .height(200)
          .margin({left:25,right:25})
          .borderRadius(20)
        }
        .width(535) // 使用固定宽度确保 Row 超出 Scroll 宽度
        .height('100%')
      }
      .width('100%')
      .height('25%')
      .scrollable(ScrollDirection.Horizontal) //设置横向滑动
      .scrollBar(BarState.Off)  //隐藏滑动条
      // .backgroundColor(Color.Orange)
      .margin({ bottom: 15 })



      CustomBookInfo({
        bookTitle: '在学词书',
        wordCount: '3000词',
        iconResourceId: $r('app.media.book')
      })
      CustomBookInfo({
        bookTitle: '配套词组',
        wordCount: '3000词',
        iconResourceId: $r('app.media.page')
      })
        .margin({bottom:15})

      CustomBookInfo({
        bookTitle: '近日已学',
        wordCount: '3000词',
        iconResourceId: $r('app.media.schedule')
      })
      CustomBookInfo({
        bookTitle: '全部已学',
        wordCount: '3000词',
        iconResourceId: $r("app.media.Yeah")
      })
        .margin({bottom:15})

      CustomBookInfo({
        bookTitle: '单词本    ',
        wordCount: '3000词',
        iconResourceId: $r('app.media.wordbook')
      })
      CustomBookInfo({
        bookTitle: '句库        ',
        wordCount: '3000词',
        iconResourceId: $r('app.media.library')
      })
      CustomBookInfo({
        bookTitle: '笔记        ',
        wordCount: '3000词',
        iconResourceId: $r('app.media.notebook')
      })


    }
    // .backgroundImageSize(1)
    .backgroundColor('#ccc')
    .width('100%')
    .height('100%')
  }
}