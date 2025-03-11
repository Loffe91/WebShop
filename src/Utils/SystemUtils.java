package Utils;

public class SystemUtils {
    public static void logoutAnimation() {
        try {
            for (int i = 0; i < 3; i++) { // Lägger till tre punkter med fördröjning
                Thread.sleep(500);
                System.out.print(".");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\nLoggat ut.");
    }

    public static void tillbakaAnimation(String menyNamn) {
        try {
            for (int i = 0; i < 3; i++) { // Lägger till tre punkter med fördröjning
                Thread.sleep(500);
                System.out.print(".");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\nGår tillbaka till " + menyNamn + ".");
    }

    public static void avslutarAnimation() {
        try {
            for (int i = 0; i < 3; i++) { // Lägger till tre punkter med fördröjning
                Thread.sleep(500);
                System.out.print(".");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\nProgrammet avslutat.");
    }
}
