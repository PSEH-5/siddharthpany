package football.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import football.dto.Competitions;
import football.dto.Country;
import football.dto.Standings;

@Service
public class TeamDetailsService {
	
	@Autowired
	private RestTemplate restTempate;
	
	public List<Standings> getStandings(String country , String leagues ,String team){
		
		List<Country> countries = getCountries();
		Country c = null;
		for(Country cntry : countries) {
			if(cntry.getCountryName().equalsIgnoreCase(country)) {
				c = cntry;
				break;
			}
		}
		if(null == c) {
			return Collections.EMPTY_LIST;
		}
		
		List<Competitions> competitions = getCompetitions(c);
		
		Competitions chosenComp = null;
		for(Competitions comp: competitions) {
			if(leagues.equalsIgnoreCase(comp.getLeagueName())) {
				chosenComp = comp;
				break;
			}
		}
		
		if(null == chosenComp) {
			return Collections.EMPTY_LIST;
		}

		String url = "https://apiv2.apifootball.com/?action=get_standings&league_id="+chosenComp.getLeagueId()+"148&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978";
		ResponseEntity<List<Standings>> response =
				restTempate.exchange(url,
		                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Standings>>() {
		            });

		List<Standings> standings = new ArrayList<Standings>();
		return standings;
	}
	
	private List<Country> getCountries(){
		ResponseEntity<List<Country>> response =
				restTempate.exchange("https://apiv2.apifootball.com/?action=get_countries&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978",
		                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Country>>() {
		            });
		List<Country> countries = response.getBody();
		return countries;
	}
	
	private List<Competitions> getCompetitions(Country country){
		String url = "https://apiv2.apifootball.com/?action=get_leagues&APIkey=9bb66184e0c8145384fd2cc0f7b914ada57b4e8fd2e4d6d586adcc27c257a978&country_id="+country.getCountryId();
		ResponseEntity<List<Competitions>> response =
				restTempate.exchange(url,
		                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Competitions>>() {
		            });
		List<Competitions> competitions = response.getBody();
		return competitions;
	}
}
