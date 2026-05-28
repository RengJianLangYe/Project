import { CustomButton } from '../components/CustomButton'
import { englishWords, EnglishWord } from '../model/English';
import router from '@ohos.router';
import { window } from '@kit.ArkUI';

@Entry
@Component
struct LearnPage {

  @State buttonText: string = 'Invest'
  @State sentenceText: string = 'He invests money in many companies and countries.'
  @State isChineseMode: boolean = false  // 控制是否显示中文
  @State currentWordIndex: number = 0    // 记录当前单词索引

  aboutToAppear(): void {
    window.getLastWindow(getContext()).then((win)=>{
      win.setWindowLayoutFullScreen(true)
    })

    // 初始化当前词
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
      Row() {
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
      .justifyContent(FlexAlign.Start)

      Blank().layoutWeight(0.2)

      // 句子
      CustomButton({
        label: this.sentenceText,
        btnWidth: '100%',
        btnHeight: '15%',
        btnFontSize: 20,
        textAlign: TextAlign.Start,
        labelMarginLeft:'5%',
        btnBackgroundColor: 'rgba(255, 255, 255, 0.7)',
        fontWeight: FontWeight.Bold,
        onPress: () => {
          router.pushUrl({ url: 'pages/LearnPage' });
        }
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
            this.isChineseMode = false;
            this.currentWordIndex = randomIndex;

            // 跳转页面（可替换为你想跳转的目标页）
            // router.pushUrl({ url: 'pages/ReviewPage' }); // 示例跳转
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
              // 已经是中文模式 → 换下一个词（英文）
              const randomIndex = Math.floor(Math.random() * englishWords.length);
              const selectedWord = englishWords[randomIndex];
              this.buttonText = selectedWord.word;
              this.sentenceText = selectedWord.sentence;
              this.isChineseMode = false;
              this.currentWordIndex = randomIndex;
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