package dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import dto.Batter;
import dto.Human;
import dto.Pitcher;
import file.FileProc;


public class MemberDao {
	
	Scanner sc = new Scanner(System.in);
	//Human에서 Object를 넣어서 호환성을 강화 시킬 수 있다.
	private Map<Integer, Human> baseMap = new HashMap<>();
	private int memberNumber;
	// FileProc클래스의 loadData()와 saveData()를 호출하기 위해서 FileProc 변수 선언.
	private FileProc fp;
	
	public MemberDao() {
		
		fp = new FileProc("baseball"); 
		fp.createFile();
		
		this.loadData();

		int arr[] = new int[baseMap.size()]; 
			
		int w = 0;
		Iterator<Integer> it = baseMap.keySet().iterator();
		//데이터가 저장된 map의 크기만큼 배열 생성.
		while(it.hasNext()) {
			Human h = baseMap.get(it.next());
			arr[w] = h.getNumber();
			w++;
		}
		//하나의 map의 데이터가를 max로 저장, 만약에 타자의 등록번호면 1000을 감소시킨다.
		int max = arr[0];
		if(max>=2000) max = max - 1000;
		
		//가장 큰 등록번호를 가진 선수의 번호를 memberNumber에 저장.
		for (int i = 0; i < arr.length; i++) {
			//타자면 1000을 감소
			if(arr[i]>=2000) {
				arr[i] = arr[i] - 1000;
			}
			if(max<arr[i]) {
				max=arr[i];
			}
		}
		//선수를 추가할 때 다음 번호를 주워야 하기 때문에 1을 증가.
		memberNumber = max + 1;
	}
	
	public void insert() {	
		// 투수/타자 ?
		System.out.print("투수(1)/타자(2) = ");
		int pos = sc.nextInt();
		
		// human
		System.out.print("이름 = ");
		String name = sc.next();
		
		System.out.print("나이 = ");
		int age = sc.nextInt();
		
		System.out.print("신장 = ");
		double height = sc.nextDouble();
				
		// 투수	1000 ~ 
		if(pos == 1) {
			// win
			System.out.print("승 = ");
			int win = sc.nextInt();
			
			// lose
			System.out.print("패 = ");
			int lose = sc.nextInt();
			
			// defense
			System.out.print("방어율 = ");
			double defence = sc.nextDouble();
			//memberNumber를 key값으로, 인스턴스를 value로 map에 데이터 저장.
			baseMap.put(memberNumber,new Pitcher(memberNumber, name, age, height, win, lose, defence));
			
		}		
		// 타자  2000 ~ 
		else if(pos == 2) {
					
			Batter bat = new Batter();			
			
			// 선수 등록 번호
		
			bat.setNumber(memberNumber + 1000);
			bat.setName(name);
			bat.setAge(age);
			bat.setHeight(height);			
						
			// 타수
			System.out.print("타수 = ");
			int batcount = sc.nextInt();
			bat.setBatcount(batcount);
						
			// 안타수
			System.out.print("안타수 = ");
			int hit = sc.nextInt();
			bat.setHit(hit);
			
			// 타율
			System.out.print("타율 = ");
			double hitAvg = sc.nextDouble();
			bat.setHitAvg(hitAvg);
			
			Human human =bat;
			//memberNumber를 key값으로, 인스턴스를 value로 map에 데이터 저장.
			baseMap.put(bat.getNumber(),human);
			
		}				
		memberNumber++;		
	}	
	
	public void delete() {
		
		System.out.print("삭제하고 싶은 선수명 입력 = ");
		String name = sc.next();
		
		if(name.equals("")) {
			System.out.println("이름을 정확히 입력해 주십시오.");
			return;		// continue
		}
		
		int findKey = search(name);
		
		if(findKey == 0) {
			System.out.println("선수 명단에 없습니다.");
		}
		// 삭제
		baseMap.remove(findKey);			
	}	
		
	public void select() {		
		System.out.print("검색하고 싶은 선수명 = ");
		String name = sc.next();
		
		if(name.equals("")) {
			System.out.println("이름을 정확히 입력해 주십시오.");
			return;		// continue
		}
		
		int findKey = search(name);
		
		if(findKey == 0) {
			System.out.println("선수 명단에 없습니다.");
		}
		else {
			Human h = baseMap.get(findKey);
					
			System.out.println("번호:" + h.getNumber());
			System.out.println("이름:" + h.getName());
			System.out.println("나이:" + h.getAge());
			System.out.println("신장:" + h.getHeight());
			
			if(h instanceof Pitcher) {
				System.out.println("승리:" + ((Pitcher)h).getWin());
				System.out.println("패전:" + ((Pitcher)h).getLose());
				System.out.println("방어율:" + ((Pitcher)h).getDefence());
			}
			else if(h instanceof Batter) {
				System.out.println("타수:" + ((Batter)h).getBatcount() );
				System.out.println("안타수:" + ((Batter)h).getHit() );
				System.out.println("타율:" + ((Batter)h).getHitAvg() );
			}
		}		
	}	
	
	public void update() {		
		
		System.out.print("수정하고 싶은 선수명 = ");
		String name = sc.next();
		
		if(name.equals("")) {
			System.out.println("이름을 정확히 입력해 주십시오.");
			return;		// continue
		}
		
		int findKey = search(name);
		
		if(findKey == 0) {
			System.out.println("선수 명단에 없습니다.");
		}
		
		Human human =baseMap.get(findKey);
		
		if(human instanceof Pitcher) {
			
			System.out.print("승 = ");
			int win = sc.nextInt();
			
			System.out.print("패 = ");
			int lose = sc.nextInt();
			
			System.out.print("방어율 = ");
			double defence = sc.nextDouble();
			
			Pitcher pit = (Pitcher)human;
			
			pit.setWin(win);
			pit.setLose(lose);
			pit.setDefence(defence);			
		}
		else if(human instanceof Batter) {
			
			System.out.print("타수 = ");
			int batcount = sc.nextInt();
			
			System.out.print("안타수 = ");
			int hit = sc.nextInt();
			
			System.out.print("타율 = ");
			double hitAvg = sc.nextDouble();
			
			Batter bat = (Batter)human;
			
			bat.setBatcount(batcount);
			bat.setHit(hit);
			bat.setHitAvg(hitAvg);			
		}		
	}
	
	public void allprint() {	
		
		Iterator<Integer> it = baseMap.keySet().iterator();
		while(it.hasNext()) {
			int key = it.next();
			//toString()이 자동호출되면서 출력.
			System.out.println(baseMap.get(key));
		}
	}	
	// name과 같은 인스턴스의 key값을 리턴.
	public int search(String name) {		
		
		int key = 0;
		Iterator<Integer> it = baseMap.keySet().iterator();
		while(it.hasNext()) {
			int k = it.next();
			if((baseMap.get(k).getName()).equals(name)) {
				key = k;
				break;
			}
		}
		return key;
	}
	
	public void saveData() {
			
		String datas[] = new String[baseMap.size()];	
		int i=0;
		Iterator<Integer> it = baseMap.keySet().iterator();
		while(it.hasNext()) {
			int key = it.next();
			// 1001-홍길동-24-178.1-10-3-0.12 형식으로 배열에 저장.
			datas[i] = baseMap.get(key).toString();
			i++;
		}	
		fp.File_save(datas);		
	}
	
	public void loadData() {
		String datas[] = fp.File_load();
			
		for (int i = 0; i < datas.length; i++) {
			
			
			String data[] = datas[i].split("-");
			
			int title = Integer.parseInt(data[0]);
			Human human = null;
			if(title < 2000) {		// 투수				
				human = new Pitcher(	Integer.parseInt(data[0]), 
										data[1], 
										Integer.parseInt(data[2]), 
										Double.parseDouble(data[3]), 
										Integer.parseInt(data[4]), 
										Integer.parseInt(data[5]), 
										Double.parseDouble(data[6]) );
			}
			else {
				human = new Batter(	Integer.parseInt(data[0]), 
										data[1], 
										Integer.parseInt(data[2]), 
										Double.parseDouble(data[3]), 
										Integer.parseInt(data[4]), 
										Integer.parseInt(data[5]), 
										Double.parseDouble(data[6]) );
			}
			baseMap.put(human.getNumber(), human);
		}	//for end
		
	}
	
	// defence_rank : 투수의 방어율을 오름차순으로 정렬.
		public void defence_rank(){
			HashMap<Integer, Human> tempMap = new HashMap<Integer, Human>(postionCheck(1));
			TreeMap<String, Human> sortMap = new TreeMap<String, Human>();
			
			Iterator<Integer> it = tempMap.keySet().iterator();
				int num =1;	
			while(it.hasNext()) {
				int key = it.next();
				Human h = tempMap.get(key);
				double k = ((Pitcher)h).getDefence();
				sortMap.put(k+" "+num, h);
			}
			
			
			Iterator<String> it2 = sortMap.keySet().iterator();
					
			while(it2.hasNext()) {
				String key = it2.next();
				Human h = sortMap.get(key);
				System.out.println(h);
			}
			
		}
		
	// hitAvg_rank() : 타자의 타율을 내림차순으로 정렬.
	public void hitAvg_rank(){
		HashMap<Integer, Human> tempMap = new HashMap<Integer, Human>(postionCheck(2));
		TreeMap<String, Human> sortMap = new TreeMap<String, Human>();
		
		Iterator<Integer> it = tempMap.keySet().iterator();
			int num =1;	
		while(it.hasNext()) {
			int key = it.next();
			Human h = tempMap.get(key);
			double k = ((Batter)h).getHitAvg();
			sortMap.put(k+" "+num, h);
		}		
		Iterator<String> it2 = sortMap.descendingKeySet().iterator();
				
		while(it2.hasNext()) {
			String key = it2.next();
			Human h = sortMap.get(key);
			System.out.println(h);
		}			
	}
		
	//  postionCheck : 매개변수가 1일 경우 투수, 매개변수가 2일 경우 타자, 그리고 그 포지션의 데이터만 맵의 저장.
	public HashMap<Integer, Human> postionCheck(int position) {
			
		//선택한 포지션의 데이터를 저장할 맵.
		HashMap<Integer, Human> posMap = new HashMap<Integer, Human>();
			
		Iterator<Integer> it = baseMap.keySet().iterator();
		while(it.hasNext()) {
			int key = it.next();
			Human h = baseMap.get(key);
			if(position==1) {
				if(h.getNumber()<2000) {
					posMap.put(key, h);
				}
			}
			else {
				if(h.getNumber()>=2000) {
					posMap.put(key, h);
				}
			}		
		}
		return posMap;		
	}
}