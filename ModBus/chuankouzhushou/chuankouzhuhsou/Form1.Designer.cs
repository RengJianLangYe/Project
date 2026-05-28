namespace chuankouzhuhsou
{
    partial class 窗口助手
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要修改
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.open_btn = new System.Windows.Forms.Button();
            this.DTS_chb = new System.Windows.Forms.CheckBox();
            this.RTS_chb = new System.Windows.Forms.CheckBox();
            this.stopbit_cbb = new System.Windows.Forms.ComboBox();
            this.label5 = new System.Windows.Forms.Label();
            this.databit_cbb = new System.Windows.Forms.ComboBox();
            this.label4 = new System.Windows.Forms.Label();
            this.check_cbb = new System.Windows.Forms.ComboBox();
            this.label3 = new System.Windows.Forms.Label();
            this.baud_cbb = new System.Windows.Forms.ComboBox();
            this.label2 = new System.Windows.Forms.Label();
            this.port_cbb = new System.Windows.Forms.ComboBox();
            this.label1 = new System.Windows.Forms.Label();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.reciveflie_txb = new System.Windows.Forms.TextBox();
            this.xzlj_btn = new System.Windows.Forms.Button();
            this.bcsj_btn = new System.Windows.Forms.Button();
            this.stop_btn = new System.Windows.Forms.Button();
            this.clear_btn = new System.Windows.Forms.Button();
            this.recivehex_chb = new System.Windows.Forms.CheckBox();
            this.autoclear_chb = new System.Windows.Forms.CheckBox();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.sendflie_txb = new System.Windows.Forms.TextBox();
            this.dkwn_btn = new System.Windows.Forms.Button();
            this.fswj_btn = new System.Windows.Forms.Button();
            this.sendclear_btn = new System.Windows.Forms.Button();
            this.send_btn = new System.Windows.Forms.Button();
            this.sendhex_chb = new System.Windows.Forms.CheckBox();
            this.autosend_chb = new System.Windows.Forms.CheckBox();
            this.groupBox4 = new System.Windows.Forms.GroupBox();
            this.groupBox5 = new System.Windows.Forms.GroupBox();
            this.statusStrip1 = new System.Windows.Forms.StatusStrip();
            this.toolStripStatusLabel1 = new System.Windows.Forms.ToolStripStatusLabel();
            this.status_tssl = new System.Windows.Forms.ToolStripStatusLabel();
            this.toolStripStatusLabel2 = new System.Windows.Forms.ToolStripStatusLabel();
            this.sendcount_tssl = new System.Windows.Forms.ToolStripStatusLabel();
            this.toolStripStatusLabel4 = new System.Windows.Forms.ToolStripStatusLabel();
            this.recivecount_tssl = new System.Windows.Forms.ToolStripStatusLabel();
            this.cleancount_tssl = new System.Windows.Forms.ToolStripStatusLabel();
            this.receive_rtb = new System.Windows.Forms.RichTextBox();
            this.send_rtb = new System.Windows.Forms.RichTextBox();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.groupBox3.SuspendLayout();
            this.groupBox4.SuspendLayout();
            this.groupBox5.SuspendLayout();
            this.statusStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.open_btn);
            this.groupBox1.Controls.Add(this.DTS_chb);
            this.groupBox1.Controls.Add(this.RTS_chb);
            this.groupBox1.Controls.Add(this.stopbit_cbb);
            this.groupBox1.Controls.Add(this.label5);
            this.groupBox1.Controls.Add(this.databit_cbb);
            this.groupBox1.Controls.Add(this.label4);
            this.groupBox1.Controls.Add(this.check_cbb);
            this.groupBox1.Controls.Add(this.label3);
            this.groupBox1.Controls.Add(this.baud_cbb);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Controls.Add(this.port_cbb);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Location = new System.Drawing.Point(12, 6);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(250, 291);
            this.groupBox1.TabIndex = 0;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "串口配置";
            // 
            // open_btn
            // 
            this.open_btn.Location = new System.Drawing.Point(112, 222);
            this.open_btn.Name = "open_btn";
            this.open_btn.Size = new System.Drawing.Size(121, 50);
            this.open_btn.TabIndex = 12;
            this.open_btn.Text = "打开串口";
            this.open_btn.UseVisualStyleBackColor = true;
            // 
            // DTS_chb
            // 
            this.DTS_chb.AutoSize = true;
            this.DTS_chb.Location = new System.Drawing.Point(28, 250);
            this.DTS_chb.Name = "DTS_chb";
            this.DTS_chb.Size = new System.Drawing.Size(61, 22);
            this.DTS_chb.TabIndex = 11;
            this.DTS_chb.Text = "DTS";
            this.DTS_chb.UseVisualStyleBackColor = true;
            // 
            // RTS_chb
            // 
            this.RTS_chb.AutoSize = true;
            this.RTS_chb.Location = new System.Drawing.Point(28, 222);
            this.RTS_chb.Name = "RTS_chb";
            this.RTS_chb.Size = new System.Drawing.Size(61, 22);
            this.RTS_chb.TabIndex = 10;
            this.RTS_chb.Text = "RTS";
            this.RTS_chb.UseVisualStyleBackColor = true;
            // 
            // stopbit_cbb
            // 
            this.stopbit_cbb.FormattingEnabled = true;
            this.stopbit_cbb.Location = new System.Drawing.Point(112, 175);
            this.stopbit_cbb.Name = "stopbit_cbb";
            this.stopbit_cbb.Size = new System.Drawing.Size(121, 26);
            this.stopbit_cbb.TabIndex = 9;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(25, 178);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(62, 18);
            this.label5.TabIndex = 8;
            this.label5.Text = "停止位";
            // 
            // databit_cbb
            // 
            this.databit_cbb.FormattingEnabled = true;
            this.databit_cbb.Location = new System.Drawing.Point(112, 139);
            this.databit_cbb.Name = "databit_cbb";
            this.databit_cbb.Size = new System.Drawing.Size(121, 26);
            this.databit_cbb.TabIndex = 7;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(25, 142);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(62, 18);
            this.label4.TabIndex = 6;
            this.label4.Text = "数据位";
            // 
            // check_cbb
            // 
            this.check_cbb.FormattingEnabled = true;
            this.check_cbb.Location = new System.Drawing.Point(112, 103);
            this.check_cbb.Name = "check_cbb";
            this.check_cbb.Size = new System.Drawing.Size(121, 26);
            this.check_cbb.TabIndex = 5;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(25, 106);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(62, 18);
            this.label3.TabIndex = 4;
            this.label3.Text = "校验位";
            // 
            // baud_cbb
            // 
            this.baud_cbb.FormattingEnabled = true;
            this.baud_cbb.Location = new System.Drawing.Point(112, 67);
            this.baud_cbb.Name = "baud_cbb";
            this.baud_cbb.Size = new System.Drawing.Size(121, 26);
            this.baud_cbb.TabIndex = 3;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(25, 70);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(62, 18);
            this.label2.TabIndex = 2;
            this.label2.Text = "波特率";
            // 
            // port_cbb
            // 
            this.port_cbb.FormattingEnabled = true;
            this.port_cbb.Location = new System.Drawing.Point(112, 31);
            this.port_cbb.Name = "port_cbb";
            this.port_cbb.Size = new System.Drawing.Size(121, 26);
            this.port_cbb.TabIndex = 1;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(25, 34);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(62, 18);
            this.label1.TabIndex = 0;
            this.label1.Text = "端口号";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.reciveflie_txb);
            this.groupBox2.Controls.Add(this.xzlj_btn);
            this.groupBox2.Controls.Add(this.bcsj_btn);
            this.groupBox2.Controls.Add(this.stop_btn);
            this.groupBox2.Controls.Add(this.clear_btn);
            this.groupBox2.Controls.Add(this.recivehex_chb);
            this.groupBox2.Controls.Add(this.autoclear_chb);
            this.groupBox2.Location = new System.Drawing.Point(12, 313);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(250, 173);
            this.groupBox2.TabIndex = 1;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "接收配置";
            // 
            // reciveflie_txb
            // 
            this.reciveflie_txb.Location = new System.Drawing.Point(6, 139);
            this.reciveflie_txb.Name = "reciveflie_txb";
            this.reciveflie_txb.Size = new System.Drawing.Size(238, 28);
            this.reciveflie_txb.TabIndex = 19;
            this.reciveflie_txb.TextChanged += new System.EventHandler(this.textBox1_TextChanged);
            // 
            // xzlj_btn
            // 
            this.xzlj_btn.Location = new System.Drawing.Point(28, 101);
            this.xzlj_btn.Name = "xzlj_btn";
            this.xzlj_btn.Size = new System.Drawing.Size(95, 30);
            this.xzlj_btn.TabIndex = 18;
            this.xzlj_btn.Text = "选择路径";
            this.xzlj_btn.UseVisualStyleBackColor = true;
            // 
            // bcsj_btn
            // 
            this.bcsj_btn.Location = new System.Drawing.Point(138, 102);
            this.bcsj_btn.Name = "bcsj_btn";
            this.bcsj_btn.Size = new System.Drawing.Size(95, 30);
            this.bcsj_btn.TabIndex = 17;
            this.bcsj_btn.Text = "保存数据";
            this.bcsj_btn.UseVisualStyleBackColor = true;
            // 
            // stop_btn
            // 
            this.stop_btn.Location = new System.Drawing.Point(138, 66);
            this.stop_btn.Name = "stop_btn";
            this.stop_btn.Size = new System.Drawing.Size(95, 30);
            this.stop_btn.TabIndex = 16;
            this.stop_btn.Text = "暂停";
            this.stop_btn.UseVisualStyleBackColor = true;
            // 
            // clear_btn
            // 
            this.clear_btn.Location = new System.Drawing.Point(138, 30);
            this.clear_btn.Name = "clear_btn";
            this.clear_btn.Size = new System.Drawing.Size(95, 30);
            this.clear_btn.TabIndex = 15;
            this.clear_btn.Text = "手动清空";
            this.clear_btn.UseVisualStyleBackColor = true;
            // 
            // recivehex_chb
            // 
            this.recivehex_chb.AutoSize = true;
            this.recivehex_chb.Location = new System.Drawing.Point(26, 68);
            this.recivehex_chb.Name = "recivehex_chb";
            this.recivehex_chb.Size = new System.Drawing.Size(106, 22);
            this.recivehex_chb.TabIndex = 14;
            this.recivehex_chb.Text = "十六进制";
            this.recivehex_chb.UseVisualStyleBackColor = true;
            // 
            // autoclear_chb
            // 
            this.autoclear_chb.AutoSize = true;
            this.autoclear_chb.Location = new System.Drawing.Point(26, 35);
            this.autoclear_chb.Name = "autoclear_chb";
            this.autoclear_chb.Size = new System.Drawing.Size(106, 22);
            this.autoclear_chb.TabIndex = 13;
            this.autoclear_chb.Text = "自动清空";
            this.autoclear_chb.UseVisualStyleBackColor = true;
            // 
            // groupBox3
            // 
            this.groupBox3.Controls.Add(this.sendflie_txb);
            this.groupBox3.Controls.Add(this.dkwn_btn);
            this.groupBox3.Controls.Add(this.fswj_btn);
            this.groupBox3.Controls.Add(this.sendclear_btn);
            this.groupBox3.Controls.Add(this.send_btn);
            this.groupBox3.Controls.Add(this.sendhex_chb);
            this.groupBox3.Controls.Add(this.autosend_chb);
            this.groupBox3.Location = new System.Drawing.Point(12, 513);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(250, 173);
            this.groupBox3.TabIndex = 20;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "发送配置";
            // 
            // sendflie_txb
            // 
            this.sendflie_txb.Location = new System.Drawing.Point(6, 139);
            this.sendflie_txb.Name = "sendflie_txb";
            this.sendflie_txb.Size = new System.Drawing.Size(238, 28);
            this.sendflie_txb.TabIndex = 19;
            // 
            // dkwn_btn
            // 
            this.dkwn_btn.Location = new System.Drawing.Point(28, 101);
            this.dkwn_btn.Name = "dkwn_btn";
            this.dkwn_btn.Size = new System.Drawing.Size(95, 30);
            this.dkwn_btn.TabIndex = 18;
            this.dkwn_btn.Text = "打开文件";
            this.dkwn_btn.UseVisualStyleBackColor = true;
            // 
            // fswj_btn
            // 
            this.fswj_btn.Location = new System.Drawing.Point(138, 102);
            this.fswj_btn.Name = "fswj_btn";
            this.fswj_btn.Size = new System.Drawing.Size(95, 30);
            this.fswj_btn.TabIndex = 17;
            this.fswj_btn.Text = "发送文件";
            this.fswj_btn.UseVisualStyleBackColor = true;
            // 
            // sendclear_btn
            // 
            this.sendclear_btn.Location = new System.Drawing.Point(138, 66);
            this.sendclear_btn.Name = "sendclear_btn";
            this.sendclear_btn.Size = new System.Drawing.Size(95, 30);
            this.sendclear_btn.TabIndex = 16;
            this.sendclear_btn.Text = "清空发送";
            this.sendclear_btn.UseVisualStyleBackColor = true;
            // 
            // send_btn
            // 
            this.send_btn.Location = new System.Drawing.Point(138, 30);
            this.send_btn.Name = "send_btn";
            this.send_btn.Size = new System.Drawing.Size(95, 30);
            this.send_btn.TabIndex = 15;
            this.send_btn.Text = "手动发送";
            this.send_btn.UseVisualStyleBackColor = true;
            // 
            // sendhex_chb
            // 
            this.sendhex_chb.AutoSize = true;
            this.sendhex_chb.Location = new System.Drawing.Point(26, 68);
            this.sendhex_chb.Name = "sendhex_chb";
            this.sendhex_chb.Size = new System.Drawing.Size(106, 22);
            this.sendhex_chb.TabIndex = 14;
            this.sendhex_chb.Text = "十六进制";
            this.sendhex_chb.UseVisualStyleBackColor = true;
            // 
            // autosend_chb
            // 
            this.autosend_chb.AutoSize = true;
            this.autosend_chb.Location = new System.Drawing.Point(26, 35);
            this.autosend_chb.Name = "autosend_chb";
            this.autosend_chb.Size = new System.Drawing.Size(106, 22);
            this.autosend_chb.TabIndex = 13;
            this.autosend_chb.Text = "自动发送";
            this.autosend_chb.UseVisualStyleBackColor = true;
            // 
            // groupBox4
            // 
            this.groupBox4.Controls.Add(this.receive_rtb);
            this.groupBox4.Location = new System.Drawing.Point(295, 6);
            this.groupBox4.Name = "groupBox4";
            this.groupBox4.Size = new System.Drawing.Size(316, 480);
            this.groupBox4.TabIndex = 21;
            this.groupBox4.TabStop = false;
            this.groupBox4.Text = "接收区";
            // 
            // groupBox5
            // 
            this.groupBox5.Controls.Add(this.send_rtb);
            this.groupBox5.Location = new System.Drawing.Point(295, 513);
            this.groupBox5.Name = "groupBox5";
            this.groupBox5.Size = new System.Drawing.Size(313, 173);
            this.groupBox5.TabIndex = 22;
            this.groupBox5.TabStop = false;
            this.groupBox5.Text = "发送区";
            // 
            // statusStrip1
            // 
            this.statusStrip1.ImageScalingSize = new System.Drawing.Size(24, 24);
            this.statusStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripStatusLabel1,
            this.status_tssl,
            this.toolStripStatusLabel2,
            this.sendcount_tssl,
            this.toolStripStatusLabel4,
            this.recivecount_tssl,
            this.cleancount_tssl});
            this.statusStrip1.Location = new System.Drawing.Point(0, 684);
            this.statusStrip1.Name = "statusStrip1";
            this.statusStrip1.Size = new System.Drawing.Size(644, 31);
            this.statusStrip1.TabIndex = 23;
            this.statusStrip1.Text = "statusStrip1";
            // 
            // toolStripStatusLabel1
            // 
            this.toolStripStatusLabel1.Name = "toolStripStatusLabel1";
            this.toolStripStatusLabel1.Size = new System.Drawing.Size(64, 24);
            this.toolStripStatusLabel1.Text = "状态：";
            // 
            // status_tssl
            // 
            this.status_tssl.Name = "status_tssl";
            this.status_tssl.Size = new System.Drawing.Size(118, 24);
            this.status_tssl.Text = "初始化正常！";
            // 
            // toolStripStatusLabel2
            // 
            this.toolStripStatusLabel2.Name = "toolStripStatusLabel2";
            this.toolStripStatusLabel2.Size = new System.Drawing.Size(100, 24);
            this.toolStripStatusLabel2.Text = "发送计数：";
            // 
            // sendcount_tssl
            // 
            this.sendcount_tssl.AutoSize = false;
            this.sendcount_tssl.Name = "sendcount_tssl";
            this.sendcount_tssl.Size = new System.Drawing.Size(50, 24);
            this.sendcount_tssl.Text = "0";
            // 
            // toolStripStatusLabel4
            // 
            this.toolStripStatusLabel4.Name = "toolStripStatusLabel4";
            this.toolStripStatusLabel4.Size = new System.Drawing.Size(100, 24);
            this.toolStripStatusLabel4.Text = "接收计数：";
            // 
            // recivecount_tssl
            // 
            this.recivecount_tssl.AutoSize = false;
            this.recivecount_tssl.Name = "recivecount_tssl";
            this.recivecount_tssl.Size = new System.Drawing.Size(50, 24);
            this.recivecount_tssl.Text = "0";
            // 
            // cleancount_tssl
            // 
            this.cleancount_tssl.Name = "cleancount_tssl";
            this.cleancount_tssl.Size = new System.Drawing.Size(82, 24);
            this.cleancount_tssl.Text = "清空计数";
            // 
            // receive_rtb
            // 
            this.receive_rtb.Dock = System.Windows.Forms.DockStyle.Fill;
            this.receive_rtb.Location = new System.Drawing.Point(3, 24);
            this.receive_rtb.Name = "receive_rtb";
            this.receive_rtb.Size = new System.Drawing.Size(310, 453);
            this.receive_rtb.TabIndex = 0;
            this.receive_rtb.Text = "";
            // 
            // send_rtb
            // 
            this.send_rtb.Dock = System.Windows.Forms.DockStyle.Fill;
            this.send_rtb.Location = new System.Drawing.Point(3, 24);
            this.send_rtb.Name = "send_rtb";
            this.send_rtb.Size = new System.Drawing.Size(307, 146);
            this.send_rtb.TabIndex = 1;
            this.send_rtb.Text = "";
            // 
            // 窗口助手
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 18F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(644, 715);
            this.Controls.Add(this.statusStrip1);
            this.Controls.Add(this.groupBox5);
            this.Controls.Add(this.groupBox4);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.Name = "窗口助手";
            this.Text = "Form1";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.groupBox3.ResumeLayout(false);
            this.groupBox3.PerformLayout();
            this.groupBox4.ResumeLayout(false);
            this.groupBox5.ResumeLayout(false);
            this.statusStrip1.ResumeLayout(false);
            this.statusStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.ComboBox port_cbb;
        private System.Windows.Forms.ComboBox baud_cbb;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ComboBox check_cbb;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.ComboBox databit_cbb;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.ComboBox stopbit_cbb;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.CheckBox RTS_chb;
        private System.Windows.Forms.CheckBox DTS_chb;
        private System.Windows.Forms.Button open_btn;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.CheckBox recivehex_chb;
        private System.Windows.Forms.CheckBox autoclear_chb;
        private System.Windows.Forms.Button clear_btn;
        private System.Windows.Forms.Button stop_btn;
        private System.Windows.Forms.Button bcsj_btn;
        private System.Windows.Forms.Button xzlj_btn;
        private System.Windows.Forms.TextBox reciveflie_txb;
        private System.Windows.Forms.GroupBox groupBox3;
        private System.Windows.Forms.TextBox sendflie_txb;
        private System.Windows.Forms.Button dkwn_btn;
        private System.Windows.Forms.Button fswj_btn;
        private System.Windows.Forms.Button sendclear_btn;
        private System.Windows.Forms.Button send_btn;
        private System.Windows.Forms.CheckBox sendhex_chb;
        private System.Windows.Forms.CheckBox autosend_chb;
        private System.Windows.Forms.GroupBox groupBox4;
        private System.Windows.Forms.GroupBox groupBox5;
        private System.Windows.Forms.StatusStrip statusStrip1;
        private System.Windows.Forms.ToolStripStatusLabel toolStripStatusLabel1;
        private System.Windows.Forms.ToolStripStatusLabel status_tssl;
        private System.Windows.Forms.ToolStripStatusLabel toolStripStatusLabel2;
        private System.Windows.Forms.ToolStripStatusLabel sendcount_tssl;
        private System.Windows.Forms.ToolStripStatusLabel toolStripStatusLabel4;
        private System.Windows.Forms.ToolStripStatusLabel recivecount_tssl;
        private System.Windows.Forms.ToolStripStatusLabel cleancount_tssl;
        private System.Windows.Forms.RichTextBox receive_rtb;
        private System.Windows.Forms.RichTextBox send_rtb;
    }
}

