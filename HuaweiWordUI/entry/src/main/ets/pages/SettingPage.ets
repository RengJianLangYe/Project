import { window } from '@kit.ArkUI';
import { GlobalState } from '../model/GlobalState';
import { User } from '../model/User';
import {CustomBookInfo } from '../components/CustomBookInfo'
//路由管理模块
import router from '@ohos.router';
import { photoAccessHelper } from '@kit.MediaLibraryKit';
import { fileIo } from '@kit.CoreFileKit';
import { uri } from '@kit.ArkTS';
import { image } from '@kit.ImageKit';
import { GlobalUserList } from '../model/GlobalUserList';

@Entry
@Component
struct SettingPage {
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

  // page B
  pageTransition() {
    // 定义页面进入时的效果，从下方侧滑入，时长为1000ms，无论页面栈发生push还是pop操作均可生效
    PageTransitionEnter({ type: RouteType.None, duration: 300 })
      .slide(SlideEffect.Left)
    // 定义页面退出时的效果，向下方侧滑出，时长为1200ms，无论页面栈发生push还是pop操作均可生效
    // PageTransitionExit({ type: RouteType.None, duration: 100 })
    //   .slide(SlideEffect.Bottom)
  }
  build() {
    Column(){
      Column(){
        //顶部返回键和邮件按钮
        Row(){
          Image($r('app.media.Return'))
            .width('10%')
            .margin({left:'5%'})
            .onClick(()=>{
              router.pushUrl({ url: 'pages/Index' }); // 跳转到 LearnPage 页面
            })
          Image($r('app.media.mail'))
            .width('10%')
            .margin({right:'5%'})
            .onClick(()=>{
              router.pushUrl({ url: 'pages/Index' }); // 跳转到 LearnPage 页面
            })
        }
        .width('100%')
        .height('8%')
        .justifyContent(FlexAlign.SpaceBetween)
        // .backgroundColor(Color.Yellow)

        //中间头像和名字
        Row(){
          Column(){
            //头像
            Image(this.currentUser?.avatarUrl ?? $r('app.media.head'))
              .width(90)
              .height(90)
              .borderRadius(50)
              .margin({bottom:'10%'})
              .onClick(()=>{
                //图片被点击
                //唤起相册对话框
                let picker=new photoAccessHelper.PhotoViewPicker()  //唤起对话框，获取url
                let options:photoAccessHelper.PhotoSelectOptions={
                  MIMEType:photoAccessHelper.PhotoViewMIMETypes.IMAGE_TYPE,
                  maxSelectNumber:1 //选择图片
                }
                picker.select(options).then((value:photoAccessHelper.PhotoSelectResult)=>{
                  let url=value.photoUris[0]  //选择图片地址
                  let file=fileIo.openSync(url)
                  //转化文件file->imageSource
                  let source:image.ImageSource=image.createImageSource(file.fd)
                  //转化文件:imageSource->pixelmap
                  let decodingOptions:image.DecodingOptions={
                    editable:true,
                      desiredPixelFormat:3
                  }
                  source.createPixelMap(decodingOptions).then((value: image.PixelMap) => {
                    // 1. 更新当前用户头像
                    this.currentUser?.setAvatarUrl(value);

                    // 2. 获取当前用户
                    const currentUser = this.currentUser;

                    // 3. 如果用户存在，则更新全局用户列表中的头像
                    if (currentUser) {
                      const username = currentUser.getName();

                      // 4. 获取全局用户管理器实例
                      const globalUserList = GlobalUserList.getInstance();

                      // 5. 查找该用户
                      const user = globalUserList.getUserByUsername(username);

                      if (user) {
                        // 6. 更新头像
                        user.setAvatarUrl(value);

                        // 7. 同步更新全局用户列表
                        globalUserList.updateUser(user);
                      }
                    }
                  });

                })
              })
            Text(this.currentUser?.username ?? '游客')
              .fontSize(25)
              .fontColor('#000000')
              .margin({bottom:'10%'})
              .fontWeight(FontWeight.Bold)
            Row(){
              Row({space: 5}){
                Image($r('app.media.vipGray'))
                  .width('15%')
                Text('未开通大会员>')
                  // .fontSize(25)
                  .fontColor('#ff545454')
                  .fontWeight(FontWeight.Bold)
              }
            }
            .width('120%')
            .height('20%')
            .borderRadius(50)
            .justifyContent(FlexAlign.SpaceAround)
            .backgroundColor('#90cccccc')
          }
          .width('40%')
          .height('60%')
          // .backgroundColor(Color.White)
        }
        .width('100%')
        .height('40%')
        .justifyContent(FlexAlign.SpaceAround)
        // .backgroundColor(Color.Blue)

        //头像下面的装备和金币
        Row({space:20}){
          //金币
          Column(){
            Row(){
              Text('酷币')
                .margin({left:'10%'})
                .fontSize(18)
                .fontWeight(FontWeight.Bold)
                .fontColor('#ff3c3c3c')
              Text('>')
                .margin({right:'10%'})
                .fontSize(20)
                .fontColor('#ff7a7a7a')
            }
            .width('100%')
            .height('25%')
            .margin({top:'8%'})
            .justifyContent(FlexAlign.SpaceBetween)
            // .backgroundColor(Color.Grey)

            //酷币数量
            Row(){
              Row({space:5}){
                Image($r('app.media.gold'))
                  .width('15%')
                Text('114,511')
                  .margin({right:'10%'})
                  .fontSize(20)
                  .fontWeight(FontWeight.Bold)
                  .fontColor('#ff3c3c3c')
              }
            }
            .width('100%')
            .height('65%')
            .justifyContent(FlexAlign.SpaceAround)
            // .backgroundColor(Color.Grey)
          }
          .width('40%')
          .height('100%')
          .borderRadius(20)
          .backgroundColor(Color.White)

          //装备
          Column(){
            Row(){
              Text('装备')
                .margin({left:'10%'})
                .fontSize(18)
                .fontWeight(FontWeight.Bold)
                .fontColor('#ff3c3c3c')
              Row({space:5}){
                Text('2/8')
                  .fontSize(15)
                  .fontColor('#ff7a7a7a')
                Text('>')
                  .margin({right:'10%'})
                  .fontSize(20)
                  .fontColor('#ff7a7a7a')
              }
            }
            .width('100%')
            .height('25%')
            .margin({top:'8%'})
            .justifyContent(FlexAlign.SpaceBetween)

            //装备图标
            Row(){
              Row({space:5}){
                Image($r('app.media.memo'))
                  .width('20%')
                Image($r('app.media.blueBook'))
                  .width('20%')
                Image($r('app.media.lineBook'))
                  .width('20%')
                Image($r('app.media.dendrogram'))
                  .width('20%')
              }
            }
            .width('100%')
            .height('65%')
            .justifyContent(FlexAlign.SpaceAround)
            // .backgroundColor(Color.Grey)

          }
          .width('50%')
          .height('100%')
          .borderRadius(20)
          .backgroundColor(Color.White)
        }
        .width('92%')
        .height('18%')
        .justifyContent(FlexAlign.SpaceAround)
        // .backgroundColor(Color.Yellow)

        Column( {space:10}){
          CustomBookInfo({
            bookTitle: '外观\\沉浸场景',
            wordCount: '',
            iconResourceId: $r('app.media.palette')
          })
            .margin({top:'2%'})
          CustomBookInfo({
            bookTitle: '学习设置',
            wordCount: '         ',
            iconResourceId: $r('app.media.switch')
          })
          CustomBookInfo({
            bookTitle: '返回登录',
            wordCount: '         ',
            iconResourceId: $r('app.media.setting')
          })
            .onClick(()=>{
              // 调用清除用户方法
              GlobalState.getInstance().clearCurrentUser()
              router.pushUrl({ url: 'pages/LoginPage' }); // 跳转到 LearnPage 页面
            })
        }
        .width('94%')
        .height('30%')
        .margin({top:'5%'})
        // .backgroundColor(Color.Gray)
      }
      .width('100%')
      .height('100%')
      .backgroundColor('rgba(255, 255, 255, 0.5)')
    }
    .backgroundImage($r('app.media.background1'))
    .backgroundImageSize(1)
    .backdropBlur(90)
    .width('100%')
    .height('100%')
  }
}