// model/English.ts

export class EnglishWord {
  word: string;           // 英文单词
  sentence: string;       // 英文句子
  wordZh: string;         // 中文单词
  sentenceZh: string;     // 中文句子

  constructor(word: string, sentence: string, wordZh: string, sentenceZh: string) {
    this.word = word;
    this.sentence = sentence;
    this.wordZh = wordZh;
    this.sentenceZh = sentenceZh;
  }
}

// 示例数据（中英对照）
export const englishWords: EnglishWord[] = [
  new EnglishWord("invest", "He invests money in many companies and countries.", "投资", "他在许多公司和国家投资。"),
  new EnglishWord("technology", "Technology is changing the world rapidly.", "科技", "科技正在迅速改变世界。"),
  new EnglishWord("environment", "We should protect the environment for future generations.", "环境", "我们应该为后代保护环境。"),
  new EnglishWord("education", "Education plays a key role in personal development.", "教育", "教育在个人发展中起着关键作用。"),
  new EnglishWord("innovation", "Innovation drives progress in every industry.", "创新", "创新推动每个行业的进步。"),

  new EnglishWord("communication", "Good communication is essential in teamwork.", "交流", "良好的交流对团队合作至关重要。"),
  new EnglishWord("strategy", "A clear strategy helps achieve long-term goals.", "策略", "清晰的策略有助于实现长期目标。"),
  new EnglishWord("efficiency", "Improving efficiency can reduce costs.", "效率", "提高效率可以降低成本。"),
  new EnglishWord("sustainability", "Sustainability is important for future growth.", "可持续性", "可持续性对未来发展很重要。"),
  new EnglishWord("leadership", "Strong leadership inspires confidence.", "领导力", "强大的领导力能激发信心。"),

  new EnglishWord("collaboration", "Team collaboration improves productivity.", "协作", "团队协作提高生产力。"),
  new EnglishWord("motivation", "Motivation comes from personal goals.", "动力", "动力来自于个人目标。"),
  new EnglishWord("creativity", "Creativity leads to innovative solutions.", "创造力", "创造力带来创新解决方案。"),
  new EnglishWord("challenge", "Every challenge is an opportunity to grow.", "挑战", "每一个挑战都是成长的机会。"),
  new EnglishWord("opportunity", "Seize opportunities when they arise.", "机会", "当机会来临时要抓住。"),

  new EnglishWord("competition", "Healthy competition encourages improvement.", "竞争", "健康的竞争促进进步。"),
  new EnglishWord("quality", "High-quality products build brand trust.", "质量", "高质量产品建立品牌信任。"),
  new EnglishWord("customer", "Customer satisfaction is our top priority.", "顾客", "顾客满意是我们的首要任务。"),
  new EnglishWord("marketing", "Marketing increases product visibility.", "市场营销", "市场营销增加产品曝光率。"),
  new EnglishWord("brand", "A strong brand builds customer loyalty.", "品牌", "一个强大的品牌建立客户忠诚度。"),

  new EnglishWord("productivity", "Increased productivity boosts profits.", "生产力", "提高生产力可提升利润。"),
  new EnglishWord("automation", "Automation reduces manual work.", "自动化", "自动化减少了人工工作。"),
  new EnglishWord("analysis", "Data analysis supports decision-making.", "分析", "数据分析支持决策制定。"),
  new EnglishWord("solution", "This solution addresses the core problem.", "解决方案", "这个解决方案解决了核心问题。"),
  new EnglishWord("feedback", "Feedback helps improve performance.", "反馈", "反馈有助于提高表现。"),

  new EnglishWord("deadline", "Meeting the deadline is crucial.", "截止日期", "按时完成任务非常关键。"),
  new EnglishWord("management", "Good management ensures smooth operations.", "管理", "良好的管理确保运营顺畅。"),
  new EnglishWord("teamwork", "Effective teamwork leads to success.", "团队合作", "高效团队合作带来成功。"),
  new EnglishWord("goal", "Set realistic goals for your project.", "目标", "为你的项目设定现实目标。"),
  new EnglishWord("progress", "Consistent effort leads to progress.", "进步", "持续努力才能取得进步。"),

  new EnglishWord("focus", "Stay focused on your main objectives.", "专注", "专注于你的主要目标。"),
  new EnglishWord("planning", "Careful planning prevents failure.", "计划", "精心规划预防失败。"),
  new EnglishWord("research", "Research provides valuable insights.", "研究", "研究提供有价值的见解。"),
  new EnglishWord("design", "User-friendly design enhances experience.", "设计", "用户友好的设计提升体验。"),
  new EnglishWord("development", "Continuous development leads to innovation.", "发展", "持续发展带来创新。"),

  new EnglishWord("network", "Building a network expands opportunities.", "网络", "建立人脉拓展机会。"),
  new EnglishWord("resource", "Use available resources wisely.", "资源", "明智地使用可用资源。"),
  new EnglishWord("organization", "Organization improves workflow efficiency.", "组织", "组织能力提升流程效率。"),
  new EnglishWord("performance", "Performance evaluations help identify strengths.", "表现", "绩效评估有助于发现优势。"),
  new EnglishWord("skill", "Developing skills enhances career growth.", "技能", "培养技能有助于职业成长。"),

  new EnglishWord("knowledge", "Knowledge empowers individuals.", "知识", "知识赋予个人力量。"),
  new EnglishWord("experience", "Experience builds expertise over time.", "经验", "经验随着时间推移积累专业技能。"),
  new EnglishWord("confidence", "Confidence is key to success.", "信心", "信心是成功的关键。"),
  new EnglishWord("discipline", "Discipline leads to consistent results.", "纪律", "纪律带来稳定的结果。"),
  new EnglishWord("commitment", "Commitment ensures long-term success.", "承诺", "承诺确保长期成功。"),

  new EnglishWord("initiative", "Taking initiative shows leadership.", "主动性", "主动精神体现领导力。"),
  new EnglishWord("responsibility", "Everyone must take responsibility.", "责任", "每个人都必须承担责任。"),
  new EnglishWord("adaptability", "Adaptability is vital in a changing world.", "适应力", "适应力在不断变化的世界中至关重要。"),
  new EnglishWord("perspective", "Understanding different perspectives enriches thinking.", "观点", "理解不同观点丰富思维。"),
  new EnglishWord("vision", "A clear vision guides strategic decisions.", "愿景", "清晰的愿景指导战略决策。"),

  new EnglishWord("ambition", "Ambition drives people to succeed.", "抱负", "抱负驱使人们取得成功。"),
  new EnglishWord("passion", "Passion fuels creativity and motivation.", "热情", "热情激发创造力和动力。"),
  new EnglishWord("patience", "Patience is the key to mastery.", "耐心", "耐心是掌握技能的关键。"),
  new EnglishWord("perseverance", "Perseverance leads to success.", "毅力", "毅力通向成功。"),
  new EnglishWord("resilience", "Resilience helps overcome challenges.", "韧性", "韧性帮助克服挑战。")
];