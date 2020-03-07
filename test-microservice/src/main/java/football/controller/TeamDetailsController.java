package football.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import football.dto.Standings;
import football.service.TeamDetailsService;

@RestController
public class TeamDetailsController {
	
	private TeamDetailsService teamDetailsService;
	
	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
	
	public List<Standings> standings(@RequestParam String country 
			 , @RequestParam String league,@RequestParam String team){
		return teamDetailsService.getStandings(country, league, team);
	}
	
	
	

}
