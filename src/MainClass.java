import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MainClass 
{
	public static void main(String[] args) throws Exception 
	{
//		String path = "C:\\Users\\WSULab\\Desktop\\InputFile.txt";
		int n1, m1  ; 
		
		//for shuffling (to create input)
		LinkedList hosList = new LinkedList(); 
		LinkedList resList = new LinkedList(); 
		
		//to take input from user
		Scanner sc = new Scanner(System.in);
		Random random = new Random();
		
		//enter path of the input file
		System.out.println("Enter Path of a text file: ");
		String path = sc.nextLine();
		
		//enter path of the output file where you want ro store output
		System.out.println("Enter Path of a output file: ");
		String path2 = sc.nextLine();
		
		//generate input then store input in input file
		BufferedWriter bw = new BufferedWriter(new FileWriter(path));
		//generate output and store it in output file
		BufferedWriter bw1 = new BufferedWriter(new FileWriter(path2));
		
		
		System.out.println("Enter number of hospitals : ");
		n1 = sc.nextInt();
		
		System.out.println("Enter number of Resident : ");
		m1 = sc.nextInt();
				 
		bw.write(n1 + " " + m1 );		
		bw.newLine();
		for (int i = 0; i < m1; i++)
		{
			hosList.add(i);
		}
		for (int i = 0; i < n1; i++)
		{
			resList.add(i);
		}
				
		Collections.shuffle(resList);
	
		//generates Hospital preference
		for (int i = 0; i < n1; i++) 
		{
			Collections.shuffle(hosList);
			
			String sb = "";

			for (int j = 0; j < hosList.size() ; j++) 
			{
				sb = sb +  "r" + hosList.get(j) + ","; 
			}
			
			if(sb.endsWith(","))
			{
				sb = sb.substring(0,sb.length() - 1);
			}
			bw.write( "h" + i + " " + (random.nextInt(m1) + 1) + ":"+ sb );
			bw.newLine();
		}
		
		//generates Resident preference
		for (int i = 0; i < m1; i++) 
		{
			Collections.shuffle(resList);
			
			String sb = "";

			for (int j = 0; j < resList.size(); j++) 
			{
				sb = sb +  "h" + resList.get(j) + ","; 
			}
			
			if(sb.endsWith(","))
			{
				sb = sb.substring(0,sb.length() - 1);
			}
			bw.write("r" + i + " :"+ sb);
			bw.newLine();
		}
		
		bw.close();
		
		int n;
		int m;
		int tmp1 = 0;
		int tmp2 = 0;

		
		
		//read input file
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();
		StringTokenizer sto = new StringTokenizer(line);
		
		n =  Integer.parseInt(sto.nextToken());
		m  =  Integer.parseInt(sto.nextToken());

		
		String lines[] = new String[n+m] ; 
		String hospital_pre[][] = new String[n][m];
		String residents_pre[][] = new String[m][n];
		
		int hospital[] = new int[n];
		int resident[] = new int[m];
		int count[] = new int[n];
		
		String hospital_List[] = new String[n];
		String resident_List[] = new String[m];
		
		HashMap<String, Integer> hm = new HashMap<>();		
		
		LinkedList<String> list = new LinkedList<>();
		
		LinkedList<String> hospiatl_List = new LinkedList<>();
		
		int vacancy[] = new int[n]; 
		
		String resident_list_for_Hospital[] = new String[n];
		
		for (int i = 0; i < resident_list_for_Hospital.length; i++) 
		{
			resident_list_for_Hospital[i] = " ";
		}
		
		try 
		{
			line = br.readLine();

		    int l = 0;
		    while (l != n+m) 
		    {
		    	lines[l] = line;
		        line = br.readLine();
		        l++;
		    }
		}
		finally 
		{
		    br.close();
		}

		for (int i = 0; i < n ; i++)
		{
			hospital[i] = -1; 
		}

		for (int i = 0; i < m; i++) 
		{
			resident[i] = -1;
		}
		
		for (int i = 0; i < n + m; i++) 
		{
			StringTokenizer st = new StringTokenizer(lines[i] , ":");

			if(i < n)
			{
				int num=0;
				
				String tmp = st.nextToken();
				
				StringTokenizer stok = new StringTokenizer(tmp);
				
				String name = stok.nextToken();
				hm.put(name, tmp1);
				hospital_List[tmp1] = name;
				list.add(name);
				hospiatl_List.add(name);
				
				vacancy[tmp1] = Integer.parseInt(stok.nextToken());
				
				String preference = st.nextToken();
			
				StringTokenizer st1 = new StringTokenizer(preference , ",");
				while (st1.hasMoreTokens()) 
				{
					hospital_pre[tmp1][num] = st1.nextToken().trim();
					num++;
				}
				tmp1++;
			}
			else
			{
				int num=0;
				String name = st.nextToken().trim();
				hm.put(name, tmp2);
				resident_List[tmp2] = name;
				String preference = st.nextToken();
			
				StringTokenizer st1 = new StringTokenizer(preference , ",");
				while (st1.hasMoreTokens()) 
				{
					residents_pre[tmp2][num] = st1.nextToken().trim();
					num++;
				}
				tmp2++;
			}
		}
	
		int inverse[][] = new int[m][n];
		
		for (int i = 0; i < m ; i++)
		{
			for (int j = 0; j < n; j++) 
			{
				inverse[i][hm.get(residents_pre[i][j])] = j;
			}
		}
		long startTime = System.currentTimeMillis();
		
		
		//algorithm starts from here
		while(!list.isEmpty())
		{
				while(!list.isEmpty())
				{
					String hos =list.getFirst();
					
					int hos_No = hm.get(hos);
					
					String res  = hospital_pre[hos_No][count[hos_No]];
					int res_No = hm.get(res);
					
					if(resident[res_No] == -1)
					{
						resident[res_No] = hos_No;
						hospital[hos_No] = res_No;
						list.removeFirst();
						count[hos_No]++;
						
						vacancy[hos_No]--;
						
						resident_list_for_Hospital[hos_No] +=  res + "";
					}
					else if(  (inverse[res_No][resident[res_No]]) > (inverse[res_No][hos_No]))
					{
						vacancy[hos_No]--;
						vacancy[resident[res_No]]++;
						
						resident_list_for_Hospital[hos_No] +=  res + "";
						resident_list_for_Hospital[resident[res_No]] = resident_list_for_Hospital[resident[res_No]].replace(res, "");

						list.removeFirst();
						list.addFirst(hospital_List[resident[res_No]]);
//						hospital[resident[res_No]] = -1;
						resident[res_No] = hos_No ;
						hospital[hos_No]= res_No;
						count[hos_No]++;
					}
					else
					{
						count[hos_No]++;
					}
					

					int r = list.size();
					for(int l = 0 ; l < r ; l++)
					{
						String tmp = list.getFirst();
						
						list.removeFirst();	
						
						if(count[hm.get(tmp)] > m - 1)
						{
						}
						else
						{
							list.add(tmp);
						}
					}
				}
				
				for (int j = 0; j < n; j++) 
				{
					String s = hospiatl_List.get(j);
					
					if( (count[hm.get(s)] > m-1)  || (vacancy[hm.get(s)] == 0) )
					{
					}
					else
					{
						list.add(s);
					}
				}
		}
		//algorithm ends here
		
		
		long endTime = System.currentTimeMillis();
		
		for (int i = 0; i < n; i++) 
		{
				bw1.write(hospital_List[i] + " : "  + resident_list_for_Hospital[i]);
				bw1.newLine();
		}
		bw1.newLine();
		
//		for (int i = 0; i < m; i++) 
//		{
//				bw1.write(resident_List[i] + " : "  + //hospital_List[resident[i]]);
//				bw1.newLine();
//		}
		bw1.close();
		
		System.out.println("Took "+(endTime - startTime) + " ms"); 			
	}
}	
