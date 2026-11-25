import java.util.Scanner;

public class RekapLaundry01 {

    static void inputData(String[] layanan, int[] hargaPerKg, int[][] data, Scanner sc) {
        for (int i = 0; i < layanan.length; i++) {
            System.out.print("Masukkan nama layanan ke-" + (i + 1) + ": ");
            layanan[i] = sc.nextLine();
            System.out.print("Masukkan harga/kg untuk " + layanan[i] + ": ");
            hargaPerKg[i] = Integer.parseInt(sc.nextLine());

            for (int j = 0; j < data[i].length; j++) {
                System.out.print("Berat cucian " + layanan[i] + " pada Hari ke-" + (j + 1) + " (kg): ");
                data[i][j] = Integer.parseInt(sc.nextLine());
            }
            System.out.println();
        }
    }

    static void tampilTabel(String[] layanan, int[] hargaPerKg, int[][] data) {
        int jumlahHari = data[0].length;
        System.out.printf("%-15s %-10s", "Layanan", "Harga/kg");
        for (int j = 0; j < jumlahHari; j++) {
            System.out.printf(" %-10s", "Hari ke-" + (j + 1));
        }
        System.out.println();

        for (int i = 0; i < layanan.length; i++) {
            System.out.printf("%-15s Rp%-8d", layanan[i], hargaPerKg[i]);
            for (int j = 0; j < data[i].length; j++) {
                System.out.printf(" %-10d", data[i][j]);
            }
            System.out.println();
        }
    }

    static int totalPerLayanan(int[][] data, int idxLayanan) {
        int total = 0;
        for (int j = 0; j < data[idxLayanan].length; j++) {
            total += data[idxLayanan][j];
        }
        return total;
    }

    static void tampilLayananTertinggi(String[] layanan, int[][] data) {
        int idxMax = 0;
        int maxTotal = totalPerLayanan(data, 0);

        for (int i = 1; i < layanan.length; i++) {
            int currTotal = totalPerLayanan(data, i);
            if (currTotal > maxTotal) {
                maxTotal = currTotal;
                idxMax = i;
            }
        }
        System.out.println("\nLayanan dengan total berat tertinggi: " + layanan[idxMax] + " (" + maxTotal + " kg)");
    }

    static int hariTersibuk(int[][] data) {
        int jumlahHari = data[0].length;
        int idxHariMax = 0;
        int maxBeratHari = 0;

        for (int i = 0; i < data.length; i++) {
            maxBeratHari += data[i][0];
        }

        for (int j = 1; j < jumlahHari; j++) {
            int totalHari = 0;
            for (int i = 0; i < data.length; i++) {
                totalHari += data[i][j];
            }
            if (totalHari > maxBeratHari) {
                maxBeratHari = totalHari;
                idxHariMax = j;
            }
        }
        return idxHariMax;
    }

    static void tampilPendapatan(String[] layanan, int[] hargaPerKg, int[][] data) {
        int jumlahHari = data[0].length;
        int totalPendapatan = 0;
        System.out.println("\n=== PENDAPATAN PER LAYANAN (" + jumlahHari + " HARI) ===");
        for (int i = 0; i < layanan.length; i++) {
            int totalKg = totalPerLayanan(data, i);
            int pendapatanLayanan = totalKg * hargaPerKg[i];
            totalPendapatan += pendapatanLayanan;
            System.out.println(layanan[i] + ": " + totalKg + " kg x Rp" + hargaPerKg[i] + " = Rp" + pendapatanLayanan);
        }
        System.out.println("Total pendapatan selama " + jumlahHari + " hari: Rp" + totalPendapatan);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Masukkan jumlah layanan: ");
        int jumlahLayanan = Integer.parseInt(sc.nextLine());
        System.out.print("Masukkan jumlah hari: ");
        int jumlahHari = Integer.parseInt(sc.nextLine());

        String[] layanan = new String[jumlahLayanan];
        int[] hargaPerKg = new int[jumlahLayanan];
        int[][] data = new int[jumlahLayanan][jumlahHari];

        inputData(layanan, hargaPerKg, data, sc);
        System.out.println("\n=== REKAP DATA LAUNDRY ===");
        tampilTabel(layanan, hargaPerKg, data);

        System.out.println("\n=== TOTAL BERAT PER LAYANAN ===");
        for (int i = 0; i < jumlahLayanan; i++) {
            System.out.println(layanan[i] + ": " + totalPerLayanan(data, i) + " kg");
        }

        tampilLayananTertinggi(layanan, data);

        int idxSibuk = hariTersibuk(data);
        System.out.println("Hari tersibuk: Hari ke-" + (idxSibuk + 1));

        tampilPendapatan(layanan, hargaPerKg, data);

        sc.close();
    }
}