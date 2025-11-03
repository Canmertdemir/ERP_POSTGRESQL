package ui;

import dao.PersonelDAO;
import model.Personel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class ERPFrame extends JFrame {
    private final PersonelDAO dao = new PersonelDAO();
    private final DefaultTableModel tableModel;
    private final JTable table;

    public ERPFrame() {
        setTitle("ERP Uygulaması - PostgreSQL");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Başlık
        JLabel lblTitle = new JLabel("PERSONEL YÖNETİM SİSTEMİ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // Tablo
        String[] columns = {"ID", "Ad", "Soyad", "Pozisyon", "Maaş", "Giriş Tarihi"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabloda düzenleme yapılmasın
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);
        table.getTableHeader().setReorderingAllowed(false);

        // Sütun genişlikleri
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Butonlar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));


// CRUD İŞLEMLERİ
        JButton btnYenile = new JButton("🔄 Yenile");
        JButton btnEkle = new JButton("➕ Yeni Personel");
        JButton btnDuzenle = new JButton("✏️ Düzenle");
        JButton btnSil = new JButton("🗑️ Sil");

        // Buton stilleri
        btnYenile.setPreferredSize(new Dimension(120, 35));
        btnEkle.setPreferredSize(new Dimension(150, 35));
        btnDuzenle.setPreferredSize(new Dimension(120, 35));
        btnSil.setPreferredSize(new Dimension(100, 35));

        btnEkle.setBackground(new Color(24, 158, 89));
        btnEkle.setForeground(Color.WHITE);
        btnDuzenle.setBackground(new Color(52, 152, 219));
        btnDuzenle.setForeground(Color.WHITE);
        btnSil.setBackground(new Color(231, 76, 60));
        btnSil.setForeground(Color.WHITE);

        buttonPanel.add(btnYenile);
        buttonPanel.add(btnEkle);
        buttonPanel.add(btnDuzenle);
        buttonPanel.add(btnSil);

        add(buttonPanel, BorderLayout.SOUTH);

        // Buton olayları
        btnYenile.addActionListener(e -> loadData());
        btnEkle.addActionListener(e -> ekle());
        btnDuzenle.addActionListener(e -> duzenle());
        btnSil.addActionListener(e -> sil());

        // İlk yüklemede verileri getir
        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0); // Tabloyu temizle

        dao.getAll().forEach(p -> {
            Object[] row = {
                    p.getId(),
                    p.getAd(),
                    p.getSoyad(),
                    p.getPozisyon(),
                    p.getMaas() != null ? String.format("%.2f ₺", p.getMaas()) : "",
                    p.getGirisTarihi() != null ? p.getGirisTarihi().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) : ""
            };
            tableModel.addRow(row);
        });

        System.out.println("✓ Personel listesi yüklendi. Toplam: " + tableModel.getRowCount());
    }

    private void ekle() {
        PersonelForm form = new PersonelForm(null);
        form.setVisible(true);

        if (form.isSaved()) {
            Personel p = form.getPersonel();
            if (dao.insert(p)) {
                JOptionPane.showMessageDialog(this,
                        "Personel başarıyla eklendi!",
                        "Başarılı",
                        JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Personel eklenirken hata oluştu!",
                        "Hata",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void duzenle() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Lütfen düzenlemek için bir personel seçin!",
                    "Uyarı",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        Personel personel = dao.getById(id);

        if (personel == null) {
            JOptionPane.showMessageDialog(this,
                    "Personel bulunamadı!",
                    "Hata",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        PersonelForm form = new PersonelForm(personel);
        form.setVisible(true);

        if (form.isSaved()) {
            if (dao.update(form.getPersonel())) {
                JOptionPane.showMessageDialog(this,
                        "Personel başarıyla güncellendi!",
                        "Başarılı",
                        JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Personel güncellenirken hata oluştu!",
                        "Hata",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void sil() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Lütfen silmek için bir personel seçin!",
                    "Uyarı",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String ad = (String) tableModel.getValueAt(selectedRow, 1);
        String soyad = (String) tableModel.getValueAt(selectedRow, 2);

        int confirm = JOptionPane.showConfirmDialog(this,
                ad + " " + soyad + " isimli personeli silmek istediğinize emin misiniz?",
                "Silme Onayı",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(id)) {
                JOptionPane.showMessageDialog(this,
                        "Personel başarıyla silindi!",
                        "Başarılı",
                        JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Personel silinirken hata oluştu!",
                        "Hata",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Sistem Look and Feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            ERPFrame frame = new ERPFrame();
            frame.setVisible(true);
        });
    }
}