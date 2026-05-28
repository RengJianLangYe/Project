using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Reflection.Emit;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace GlobalHook
{
    public partial class frmMain : Form
    {
        public static List<Button> buttons = new List<Button> { };
        private static string _lastChar = "";
        public static string Rec = "";
        private Random random;
        public frmMain()
        {
            InitializeComponent();
            this.MouseDown += FrmMain_MouseDown;
            this.MouseMove += FrmMain_MouseMove;
            this.MouseUp += FrmMain_MouseUp;
            random = new Random();
            this.TopMost = !this.TopMost;
            // 遍历窗体上的所有控件
            foreach (Control control in this.Controls)
            {
                // 如果控件是按钮，则添加到列表中
                if (control is Button button)
                {
                    if(button.Tag != null)
                    {
                        buttons.Add(button);
                    }                    
                }
            }
            Task.Run(() =>
            {
                try
                {
                    while (true)
                    {
                        // 生成随机颜色
                        Color randomForeColor = Color.FromArgb(random.Next(256), random.Next(256), random.Next(256));
                        // 模拟耗时操作
                        System.Threading.Thread.Sleep(100);
                        // 在 UI 线程上更新 UI
                        this.Invoke(new Action(() =>
                        {
                            labTxt.ForeColor = randomForeColor;
                            labTxt.Text = _lastChar.ToString();
                            txtRec.Text = Rec;
                        }));
                    }
                }
                catch (Exception e)
                {

                }
                
                
            });

        }

        private void frmMain_Load(object sender, EventArgs e)
        {
            GlobalKeyBoarHook._hookID = GlobalKeyBoarHook.SetHook(GlobalKeyBoarHook._proc);
        }

        private void btnClose_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        static public void XiaozhiKeyUp(KeyEventArgs e)
        {
            // 查找与按键匹配的按钮并且熄灭它
            Button xiaozhi =  buttons.FirstOrDefault(btn => e.KeyCode == (Keys)int.Parse((string)btn.Tag));
            if ((xiaozhi == null))
            {
                return;
            }

            xiaozhi.BackColor = Color.White;
        }

        static public void XiaozhiKeyDown(KeyEventArgs e)
        {
            // 查找与按键匹配的按钮并点亮它
            Button xiaozhi = buttons.FirstOrDefault(btn => e.KeyCode == (Keys)int.Parse(btn.Tag.ToString()));
            if ((xiaozhi == null))
            {
                return;
            }
            if (xiaozhi.Text == _lastChar)
            {
                return;
            }
            xiaozhi.BackColor = Color.Pink;
            txtAdd(xiaozhi.Text);
        }
        private static void txtAdd(string temp)
        {
            Rec += temp;
            _lastChar = temp;
        }

        private void timertxt_Tick(object sender, EventArgs e)
        {
            if(Rec.Length > 0)
            {
                if (Rec != "")
                {
                    Rec = Rec.Substring(1);
                }
            }            
        }

        private void timerlab_Tick(object sender, EventArgs e)
        {
            if (labTxt.Text.Length > 0)
            {
                if (labTxt.Text == _lastChar)
                {
                    _lastChar = "" ;
                }
            }
        }

        private bool isDragging = false; // 是否正在拖动
        private Point dragStartPoint;   // 拖动起始点

        // 鼠标按下时触发
        private void FrmMain_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left) // 仅响应左键
            {
                isDragging = true;
                dragStartPoint = new Point(e.X, e.Y); // 记录拖动起始点
            }
        }

        // 鼠标移动时触发
        private void FrmMain_MouseMove(object sender, MouseEventArgs e)
        {
            if (isDragging)
            {
                // 计算窗体新位置
                Point newLocation = this.PointToScreen(new Point(e.X, e.Y));
                newLocation.Offset(-dragStartPoint.X, -dragStartPoint.Y);

                // 更新窗体位置
                this.Location = newLocation;
            }
        }

        // 鼠标松开时触发
        private void FrmMain_MouseUp(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left) // 仅响应左键
            {
                isDragging = false; // 结束拖动
            }
        }
    }
}
