package solve;

import java.io.*;

public class Main
{
    public static void main(String []args)
    {
        String []info = ReadInfo();
        Reader reader;
        NewFile f = new NewFile();
        f.Create();
        Writer writer;

        try
        {
            BufferedReader fileReader = new BufferedReader(new FileReader(new File(info[0])));
            reader = new Reader(fileReader);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("result.txt"), true));
            writer = new Writer(bufferedWriter);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        Parser.Process(reader, writer, Integer.parseInt(info[1]), Integer.parseInt(info[2]));
        writer.Close();
    }

    private static String[] ReadInfo()  //метод-опросник
    {
        String []info = new String[3];
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter path to file: ");
            info[0] = reader.readLine();

            System.out.println("Enter size of retreat: ");
            while (true)
            {
                try
                {
                    info[1] = reader.readLine();
                    Integer.parseInt(info[1]);
                    break;
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Bad size! Try again: ");
                }
            }

            System.out.println("Choice style:\r\n1) if(){\r\n   }\r\n2) if()\r\n   {}");
            while (true)
            {
                try
                {
                    info[2] = reader.readLine();
                    Integer.parseInt(info[2]);
                    break;
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Bad type! Try again: ");
                }
            }
            reader.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        return info;
    }

}
