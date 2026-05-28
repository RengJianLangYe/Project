// components/CustomButton.ets

@Component
struct CustomButton {
  @Prop label: string = "按钮"
  fontWeight: FontWeight = FontWeight.Bold
  btnWidth: string = '40%'
  btnHeight: string = '100px'
  @Prop btnBackgroundColor: string = 'rgba(255, 255, 255, 0.7)'
  btnFontSize: number = 25
  btnBackgroundImage: Resource | undefined = undefined
  onPress: () => void = () => {}

  // 新增属性：文本对齐方式，默认居中
  @Prop textAlign: TextAlign = TextAlign.Center

  // 新增属性：左侧 margin，默认为 0%
  @Prop labelMarginLeft: string = '0%'

  build() {
    Button({ type: ButtonType.Normal, stateEffect: true }) {
      Text(this.label)
        .fontSize(this.btnFontSize)
        .fontColor('#000000')
        .fontWeight(this.fontWeight)
        .textAlign(this.textAlign)       // 使用传入的对齐方式
        .maxLines(3)                     // 最多显示 3 行
        .width('100%')                   // 占满整个按钮宽度
        .height('100%')                  // 占满整个按钮高度
        .margin({ left: this.labelMarginLeft }) // 可选左侧 margin
    }
    .width(this.btnWidth)
    .height(this.btnHeight)
    .backgroundColor(this.btnBackgroundColor)
    .backgroundImage(this.btnBackgroundImage)
    .borderRadius(8)
    .onClick(() => {
      this.onPress()
    })
  }
}

export { CustomButton }