package Krypt;

import java.util.Random;

public class NKRypt {
  
  public static boolean CYPHER = true;
  public static boolean DECYPHER = false;
  
  public static String caesar(boolean mode, String text, int n) {
    StringBuilder sb = new StringBuilder();
    if (mode) {
      for (char i : text.toCharArray()) {
        int x = i;
        sb.append((char)(x + n));
      }
      return sb.toString();
    }
    for (char i : text.toCharArray()) {
      int x = i;
      sb.append((char)(x - n));
    }
    return sb.toString();
  }
  
  public static String vigenere(boolean mode, String text, String key)
  {
    StringBuilder sb = new StringBuilder();
    int auxIndex = 0;
    if (mode) {
      for (int i = 0; i < text.length(); i++) {
        sb.append(String.valueOf((char)(text.charAt(i) + key.charAt(auxIndex))));
        if (auxIndex < key.length() - 1) {
          auxIndex++;
        } else {
          auxIndex = 0;
        }
      }
      return sb.toString();
    }
    for (int i = 0; i < text.length(); i++) {
      sb.append(String.valueOf((char)(text.charAt(i) - key.charAt(auxIndex))));
      if (auxIndex < key.length() - 1) {
        auxIndex++;
      } else {
        auxIndex = 0;
      }
    }
    return sb.toString();
  }
  
  public static String mirror(String text)
  {
    StringBuilder sb = new StringBuilder();
    for (int i = text.length() - 1; i >= 0; i--) {
      sb.append(text.charAt(i));
    }
    return sb.toString();
  }
  
  public static String simplex(boolean mode, String text, int n) {
    StringBuilder sb = new StringBuilder();
    if (mode) {
      for (int i = 0; i < n; i++) {
        for (int j = i; j < text.length(); j += n) {
          sb.append(text.charAt(j));
        }
      }
      return sb.toString();
    }
    int line = text.length() / n;
    int lineExtra = text.length() % n;
    int aux = lineExtra;
    
    for (int i = 0; sb.length() < text.length(); i++) {
      for (int j = i; (j < text.length()) && (sb.length() < text.length()); j += line) {
        if ((lineExtra > 0) && (j > i)) {
          lineExtra--;
          if (j + 1 < text.length()) j++;
        }
        sb.append(text.charAt(j));
      }
      lineExtra = aux;
    }
    return sb.toString();
  }
  
  public static String column(boolean mode, String text, String key) {
    StringBuilder sb = new StringBuilder();
   
    char[] aux = key.toCharArray();
    if (mode) {
      int height = 0;
      int x = 0;
      int originalLength = text.length();
      char[][] col;
      if (text.length() % key.length() > 0) {
        col = new char[text.length() / key.length() + 2][key.length()];
        height = text.length() / key.length() + 2;
      } else {
        col = new char[text.length() / key.length() + 1][key.length()];
        height = text.length() / key.length() + 1;
      }
      
      int newLength = text.length() % key.length() > 0 ? (text.length() + 1) * key.length() : text.length() * key.length();
      
      Random rnd = new Random();
      
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < key.length(); j++) {
          if (i == 0) {
            col[i][j] = key.charAt(j);
          } else {
            try {
              col[i][j] = text.charAt(x);
            } catch (Exception e) {
              col[i][j] = ((char)(rnd.nextInt(25) + 65));
            }
            x++;
          }
        }
      }
      
      java.util.Arrays.sort(aux);
      x = 0;
      
      for (int i = 0; (i < key.length()) && (x < key.length()); i++) {
        if (aux[x] == col[0][i]) {
          for (int j = 1; j < height; j++) {
            sb.append(String.valueOf(col[j][i]));
          }
          if (sb.length() < newLength) {
            i = -1;
          }
          x++;
        }
      }
      
      sb.append((char)originalLength);
      
      return sb.toString();
    }
    
    int originalLength = text.charAt(text.length() - 1);
    text = text.substring(0, text.length() - 1);
    
    java.util.Arrays.sort(aux);
    
    int x = 0;
    int height;
    char[][] col; 
    char[][] realCol;
    
    if (originalLength % key.length() > 0) {
      col = new char[originalLength / key.length() + 2][key.length()];
      realCol = new char[originalLength / key.length() + 2][key.length()];
      height = originalLength / key.length() + 2;
    } else {
      col = new char[originalLength / key.length() + 1][key.length()];
      realCol = new char[originalLength / key.length() + 1][key.length()];
      height = originalLength / key.length() + 1;
    }
    
    for (int i = 0; i < key.length(); i++) {
      for (int j = 0; j < height; j++) {
        if (j == 0) {
          col[j][i] = aux[i];
        } else {
          try {
            col[j][i] = text.charAt(x);
            x++;
          }
          catch (Exception localException1) {}
        }
      }
    }
    

    x = 0;
    
    for (int i = 0; (i < key.length()) && (x < key.length()); i++) {
      if (col[0][i] == key.charAt(x)) {
        for (int j = 0; j < height; j++) {
          realCol[j][x] = col[j][i];
          sb.append(realCol[j][i]);
        }
        if (sb.length() < text.length() + key.length()) {
          i = -1;
        }
        x++;
      }
    }
    
    sb.setLength(0);
    
    for (int i = 1; i < height; i++) {
      for (int j = 0; j < key.length(); j++) {
        if (sb.length() >= originalLength) break;
        sb.append(realCol[i][j]);
      }
    }
    



    return sb.toString();
  }
  
  public static String mask(boolean mode, String text) throws Exception
  {
    StringBuilder sb = new StringBuilder();
    

    boolean[][] mask = {
      { true, false, true }, 
      { false, true, true, false, false, false, false, true }, 
      new boolean[8], 
      new boolean[8], 
      { false, false, false, true, false, true }, 
      new boolean[8], 
      { false, false, false, false, false, false, false, true }, 
      { false, false, false, false, true } };
    


    if (mode) {
      if (text.length() > 26) {
        throw new Exception();
      }
      
      int originalLength = text.length();
      
      Random rnd = new Random();
      while (text.length() < 36) {
        text = text + (char)(rnd.nextInt(25) + 65);
      }
      
      char[][] txt = new char[8][8];
      
      int n = 0;
      
      for (int x = 0; x < 4; x++) {
        for (int i = 0; i < 8; i++) {
          for (int j = 0; j < 8; j++) {
            if (mask[i][j] && n < 36) {
              txt[i][j] = text.charAt(n);
              n++;
            }
          }
        }
        mask = rotate(mask, 8);
      }
      
      for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
          if ((i == 7) && (j == 7)) {
            sb.append((char)originalLength);
          } else {
            sb.append(txt[i][j]);
          }
        }
      }
      
      return sb.toString();
    }
    if (text.length() != 64) {
      throw new Exception();
    }
    
    int originalLength = text.charAt(63);
    
    int n = 0;
    
    for (int x = 0; x < 4; x++) {
      for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
          if (mask[i][j] && sb.length() < originalLength) sb.append(text.charAt(n));
          n++;
        }
      }
      mask = rotate(mask, 8);
      n = 0;
    }
    
    return sb.toString();
  }
  

  private static boolean[][] rotate(boolean[][] matrix, int n)
  {
    boolean[][] ret = new boolean[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        ret[i][j] = matrix[(n - j - 1)][i];
      }
    }
    return ret;
  }
}
