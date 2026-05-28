import { CustomButton } from '../components/CustomButton'
import { englishWords, EnglishWord } from '../model/English';
import router from '@ohos.router';
import { window } from '@kit.ArkUI';

@Entry
@Component
struct ReviewPage {

  @State buttonText: string = 'Invest'
  @State sentenceText: string = 'He invests money in many companies and countries.'
  @State isChineseMode: boolean = false  // 控制是否显示中文
  @State currentWordIndex: number = 0    // 当前单词索引

  aboutToAppear(): void {
    window.getLastWindow(getContext()).then((win)=>{
      win.setWindowLayoutFullScreen(true)
    })

    // 初始化默认显示第一个单词
    const selectedWord = englishWords[this.currentWordIndex]
    this.buttonText = selectedWord.word
    this.sentenceText = selectedWord.sentence
  }

  build() {
    Column() {
      // 返回按钮
      Column() {
        Image($r('app.media.Return'))
          .width(40)
          .height(40)
          .margin({ left: '1%' })
          .onClick(()=>{
            router.pushUrl({ url: 'pages/Index' });
          })
      }
      .width('100%')
      .height('10%')
      .margin({top:'5%'})
      .alignItems(HorizontalAlign.Start)

      Blank().layoutWeight(0.0)

      // 单词
      Column({ space: 20 }) {
        CustomButton({
          label: this.buttonText,
          btnWidth: '100%',
          btnHeight: '15%',
          btnFontSize: 35,
          textAlign: TextAlign.Start,
          labelMarginLeft: '10%',
          btnBackgroundColor: 'rgba(255, 255, 255, 0.0)',
          onPress: () => {}
        })
      }
      .width('100%')
      .alignItems(HorizontalAlign.Start)

      Blank().layoutWeight(0.2)

      // 句子
      CustomButton({
        label: this.sentenceText,
        btnWidth: '100%',
        btnHeight: '15%',
        btnFontSize: 20,
        textAlign: TextAlign.Start,
        labelMarginLeft: '5%',
        btnBackgroundColor: 'rgba(255, 255, 255, 0.7)',
        fontWeight: FontWeight.Bold,
        onPress: () => {}
      })

      Blank().layoutWeight(0.8)

      // 底部按钮区域
      Row({ space: 15 }) {
        // 认识按钮：跳过当前词，随机显示下一个英文单词
        CustomButton({
          label: '认识',
          btnWidth: '40%',
          btnHeight: '100%',
          btnFontSize: 25,
          btnBackgroundColor: 'rgba(255, 255, 255, 0.0)',
          fontWeight: FontWeight.Bold,
          onPress: () => {
            const randomIndex = Math.floor(Math.random() * englishWords.length);
            const selectedWord = englishWords[randomIndex];
            this.buttonText = selectedWord.word;
            this.sentenceText = selectedWord.sentence;
            this.currentWordIndex = randomIndex;
            this.isChineseMode = false;
          }
        })

        // 不认识按钮：显示中文翻译
        CustomButton({
          label: '不认识',
          btnWidth: '40%',
          btnHeight: '100%',
          btnFontSize: 25,
          btnBackgroundColor: 'rgba(255, 255, 255, 0.0)',
          fontWeight: FontWeight.Bold,
          onPress: () => {
            if (this.isChineseMode) {
              // 已经是中文模式 → 换下一个词并恢复英文
              const randomIndex = Math.floor(Math.random() * englishWords.length);
              const selectedWord = englishWords[randomIndex];
              this.buttonText = selectedWord.word;
              this.sentenceText = selectedWord.sentence;
              this.currentWordIndex = randomIndex;
              this.isChineseMode = false;
            } else {
              // 英文模式 → 显示当前词的中文翻译
              const currentWord = englishWords[this.currentWordIndex];
              this.buttonText = currentWord.wordZh;
              this.sentenceText = currentWord.sentenceZh;
              this.isChineseMode = true;
            }
          }
        })
      }
      .width('100%')
      .height('10%')
      .justifyContent(FlexAlign.Center)
      .margin({ bottom: '10%' })
    }
    .width('100%')
    .height('100%')
    .backgroundImage($r('app.media.background1'))
    .backgroundImageSize(1)
    .backdropBlur(30)
  }
}