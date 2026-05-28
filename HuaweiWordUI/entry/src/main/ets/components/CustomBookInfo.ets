@Component
struct CustomBookInfo {
  @Prop bookTitle: string = ''
  @Prop wordCount: string = ''
  @Prop iconResourceId: Resource | undefined = undefined

  build() {
    Row({ space: 10 }) {
      Image(this.iconResourceId)
        .width(35)
        .height(35)
        .margin({left:15})
        .objectFit(ImageFit.Cover)

      Text(this.bookTitle)
        .fontSize(20)
        .fontColor('#000000')
        // .fontWeight(FontWeight.Bold)
        .textAlign(TextAlign.Center)
        .margin({right:'23%'})

      Text(this.wordCount)
        .fontSize(20)
        .fontColor('#888888')
        // .fontWeight(FontWeight.Bold)
        // .margin({right:'0%'})

      //改成image吧
      Image($r('app.media.return2'))
        .width(20)
        .height(20)
        // .margin({right:'5%'})

    }
    .width("95%")
    .height(65)
    .backgroundColor(Color.White)
    .borderRadius(15)
  }
}
export { CustomBookInfo  }