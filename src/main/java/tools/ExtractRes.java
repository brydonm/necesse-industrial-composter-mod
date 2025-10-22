package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import necesse.engine.modLoader.InputStreamSupplier;
import necesse.gfx.res.GameStreamReader;
import necesse.gfx.res.ResourceFile;
import necesse.gfx.res.ResourceFolder;

public class ExtractRes {
    public static void main(String[] args) throws Exception {
        String gameDir = System.getProperty("gameDir", "C:/SteamLibrary/steamapps/common/Necesse");
        String resDataPath = args.length > 0 ? args[0] : gameDir + "/res.data";
        String searchKey = args.length > 1 ? args[1] : "objects/compostbin";
        String outputDir = args.length > 2 ? args[2] : "extracted";

        ResourceFolder folder = new ResourceFolder();

        InputStreamSupplier supplier = new InputStreamSupplier() {
            @Override
            public InputStream get() throws IOException {
                return new FileInputStream(resDataPath);
            }
        };

        try (GameStreamReader reader = new GameStreamReader(supplier)) {
            folder.read(reader);
        }

        List<Map.Entry<String, ResourceFile>> matches = findMatches(folder, searchKey);
        if (matches.isEmpty()) {
            System.out.println("No resources matched: " + searchKey);
            printHints(folder, searchKey);
            return;
        }

        int extracted = 0;
        for (Map.Entry<String, ResourceFile> e : matches) {
            String path = e.getKey();
            ResourceFile rf = e.getValue();
            byte[] data = rf.loadBytes(false);
            Path outPath = Paths.get(outputDir, path);
            Files.createDirectories(outPath.getParent());
            try (FileOutputStream fos = new FileOutputStream(outPath.toFile())) {
                fos.write(data);
            }
            extracted++;
            System.out.println("Extracted: " + outPath.toString());
        }

        System.out.println("Done. Extracted files: " + extracted);
    }

    private static List<Map.Entry<String, ResourceFile>> findMatches(ResourceFolder folder, String key) {
        String keyLower = key.toLowerCase(Locale.ROOT);
        List<String> candidates = new ArrayList<>();
        candidates.add(keyLower);
        if (!keyLower.endsWith(".png")) candidates.add(keyLower + ".png");
        if (!keyLower.endsWith(".wav")) candidates.add(keyLower + ".wav");

        List<Map.Entry<String, ResourceFile>> res = new ArrayList<>();
        for (Map.Entry<String, ResourceFile> e : folder.files.entrySet()) {
            String p = e.getKey().toLowerCase(Locale.ROOT);
            for (String c : candidates) {
                if (p.equals(c) || p.endsWith("/" + c)) {
                    res.add(e);
                    break;
                }
            }
        }
        return res;
    }

    private static void printHints(ResourceFolder folder, String key) {
        String folderPart = key.contains("/") ? key.substring(0, key.lastIndexOf('/')) : key;
        List<String> sample = new ArrayList<>();
        for (String p : folder.files.keySet()) {
            if (p.startsWith(folderPart) && (p.contains("compost") || p.contains("Compost"))) {
                sample.add(p);
                if (sample.size() >= 10) break;
            }
        }
        if (!sample.isEmpty()) {
            System.out.println("Did you mean one of:");
            for (String s : sample) System.out.println("  " + s);
        }
    }
}


