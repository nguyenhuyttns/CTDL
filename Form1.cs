using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Runtime;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml;

namespace OnTX2_B2
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            HienThi();
        }

        XmlDocument doc = new XmlDocument();
        string tentep = @"D:\TichHop\Project\OnTX2\OnTX2_B2\OnTX2_B2\dsnhanvien.xml";
        int d;

        private void HienThi()
        {
            tbnhanvien.Rows.Clear();
            doc.Load(tentep);

            XmlNodeList DS = doc.SelectNodes("/dsnv/nhanvien");
            tbnhanvien.ColumnCount = 4;
            tbnhanvien.Rows.Add();
            int sd = 0;


            foreach (XmlNode nhanvien in DS)
            {
                XmlNode manv =  nhanvien.SelectSingleNode("@manv");
                tbnhanvien.Rows[sd].Cells[0].Value = manv.InnerText.ToString();
                XmlNode ho = nhanvien.SelectSingleNode("hoten/ho");
                tbnhanvien.Rows[sd].Cells[1].Value = ho.InnerText.ToString();
                XmlNode ten = nhanvien.SelectSingleNode("hoten/ten");
                tbnhanvien.Rows[sd].Cells[2].Value = ten.InnerText.ToString();
                XmlNode diachi = nhanvien.SelectSingleNode("diachi");
                tbnhanvien.Rows[sd].Cells[3].Value = diachi.InnerText.ToString();

                tbnhanvien.Rows.Add();
                sd++;
            }


        }


        private void btnsua_Click(object sender, EventArgs e)
        {
            doc.Load(tentep);
            XmlElement goc = doc.DocumentElement;
            XmlNode nv_cu = goc.SelectSingleNode("/dsnv/nhanvien[@manv='" + txtmanv.Text + "']");
            XmlNode nv_moi = doc.CreateElement("nhanvien");

            XmlAttribute manv = doc.CreateAttribute("manv");
            manv.InnerText = txtmanv.Text;
            nv_moi.Attributes.Append(manv);

            XmlNode hoten = doc.CreateElement("hoten");
            XmlNode ho = doc.CreateElement("ho");
            ho.InnerText = txtho.Text;
            hoten.AppendChild(ho);
            XmlNode ten = doc.CreateElement("ten");
            ten.InnerText = txtho.Text;
            hoten.AppendChild(ten);
            nv_moi.AppendChild(hoten);

            XmlNode diachi = doc.CreateElement("diachi");
            diachi.InnerText = txtho.Text;
            nv_moi.AppendChild(diachi);

            goc.ReplaceChild(nv_moi, nv_cu);
            doc.Save(tentep);
            HienThi();


        }

        private void btnthem_Click(object sender, EventArgs e)
        {
            doc.Load(tentep);

            XmlElement goc = doc.DocumentElement;

            XmlNode nhanvien = doc.CreateElement("nhanvien");

            XmlAttribute manv = doc.CreateAttribute("manv");
            manv.InnerText = txtmanv.Text;
            nhanvien.Attributes.Append(manv);

            XmlNode hoten = doc.CreateElement("hoten");
            XmlNode ho = doc.CreateElement("ho");
            ho.InnerText = txtho.Text;
            hoten.AppendChild(ho);
            XmlNode ten = doc.CreateElement("ten");
            ten.InnerText = txtho.Text;
            hoten.AppendChild(ten);
            nhanvien.AppendChild(hoten);

            XmlNode diachi = doc.CreateElement("diachi");
            diachi.InnerText = txtho.Text;
            nhanvien.AppendChild(diachi);

            goc.AppendChild(nhanvien);
            doc.Save(tentep);
            HienThi();


        }

        private void btnxoa_Click(object sender, EventArgs e)
        {
            doc.Load(tentep);
            XmlElement goc = doc.DocumentElement;
            XmlNode nv_xoa = goc.SelectSingleNode("/dsnv/nhanvien[@manv='" + txtmanv.Text + "']");
            goc.RemoveChild(nv_xoa);
            doc.Save(tentep);
            HienThi() ;


        }

        private void tbnhanvien_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            d = e.RowIndex;
            txtmanv.Text = tbnhanvien.Rows[d].Cells[0].Value.ToString();
            txtho.Text = tbnhanvien.Rows[d].Cells[1].Value.ToString();
            txtten.Text = tbnhanvien.Rows[d].Cells[2].Value.ToString();
            txtdiachi.Text = tbnhanvien.Rows[d].Cells[3].Value.ToString();

        }

        private void btntim_Click(object sender, EventArgs e)
        {
            doc.Load(tentep);
            XmlNodeList DS = doc.SelectNodes("/dsnv/nhanvien");

            XmlNode timthay = null;

            foreach(XmlNode nhanvien in DS)
            {
                XmlNode manv = nhanvien.Attributes["manv"];
                if (manv != null && manv.InnerText == txtmanv.Text)
                {
                    timthay = nhanvien;
                    break;
                }

                
            }

            if (timthay != null)
            {
                tbnhanvien.Rows.Clear();
                tbnhanvien.ColumnCount = 4;
                tbnhanvien.Rows.Add();

                tbnhanvien.Rows[0].Cells[0].Value = timthay.Attributes["manv"].InnerText;
                tbnhanvien.Rows[0].Cells[1].Value = timthay.SelectSingleNode("hoten/ho").InnerText;
                tbnhanvien.Rows[0].Cells[2].Value = timthay.SelectSingleNode("hoten/ten").InnerText;
                tbnhanvien.Rows[0].Cells[3].Value = timthay.SelectSingleNode("diachi").InnerText;


            }
            else
                MessageBox.Show("KHONG TIM THAY CUON SACH CO MA: " + txtmanv.Text);




        }
    }
}
