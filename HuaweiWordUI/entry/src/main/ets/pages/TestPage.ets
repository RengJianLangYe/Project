@Entry
@Component
struct TestPage {
  build() {
    Row() {
      //图标图片
      Image($r('app.media.book'))
        .width('15%')
        .height('50%')
        .objectFit(ImageFit.Cover)
        .margin({ left: 10 }) // 靠左

      Text('在学词书')
        .fontSize(25)
        .fontColor(Color.Black)
        .fontWeight(FontWeight.Bold)
        .textAlign(TextAlign.Center)
        .margin({ left: 5 })

      Text('4775词')
        .fontSize(20)
        .fontColor(Color.Gray)
        .fontWeight(FontWeight.Bold)
        .margin({ left: 60 })

      //改成image吧
      Image($r('app.media.return1'))
        .width(20)
        .height(20)

    }
    .width("95%")
    .height(100)
    .borderRadius(20)
    // .backgroundColor(Color.Red)
    // .justifyContent(FlexAlign.SpaceBetween) // 左右两端对齐
    // .alignItems(HorizontalAlign.Start) // 垂直居中
  }
}