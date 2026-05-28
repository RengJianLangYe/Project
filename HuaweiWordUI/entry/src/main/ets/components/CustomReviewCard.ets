@Component
struct CustomReviewCard {
  @Prop BackgroundColor: string = '#e2fffbfb' // 背景色
  @Prop iconResourceId: Resource = $r('app.media.play') // 图标资源
  @Prop title: string = '回顾' // 标题
  @Prop wordCount: string = '0词' // 副标题

  build() {
    Column() {
      // 标题与副标题区域
      Column() {
        Text(this.title)
          .fontWeight(FontWeight.Bold)
          .fontSize(20)

        Text(this.wordCount)
          .fontWeight(FontWeight.Bold)
          .fontColor(Color.Gray)
          .fontSize(15)
      }
      .width(100)
      .height(50)
      .margin({ top: 5 })
      .alignItems(HorizontalAlign.Start) // 水平靠左对齐 [[10]]

      // 隔断区域（空白行）
      Row()
        .width('80%')
        .height('15%')

      // 图标区域
      Row() {
        Image(this.iconResourceId)
          .width('30%')
      }
      .width('80%')
      .height('33%')
      .justifyContent(FlexAlign.End) // 图标靠右显示
    }
    .width('48%')
    .height('90%')
    .borderRadius(10)
    .backgroundColor(this.BackgroundColor)
  }
}

export {CustomReviewCard}