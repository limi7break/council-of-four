package it.polimi.ingsw.ps13.model.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import it.polimi.ingsw.ps13.model.UnexpectedResultException;
import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.city.City;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PoliticsCardDeck;

public class Board implements Serializable {

	private static final long serialVersionUID = 0L;
	
	private static final int COUNCILLORS_PER_BALCONY = 4;
	
	private Set<City> cities;
	private final Region coastRegion;
	private final Region hillRegion;
	private final Region mountainRegion;
	private final King king;
	private final CouncillorBalcony kingCouncil;
	private final Bonus kingRewardTile;
	private final NobilityTrack nobilityTrack;
	private final PoliticsCardDeck politicsCardDeck;
	private final Collection<Councillor> availableCouncillors;
	
	/**
	 * If the alphabetical order for the cities in the regions in required, the set of cities loaded has to be sorted.
	 * If the number of cities is not a multiple of 3, the Mountain region will have more cities than
	 * Coast and Hill.
	 * 
	 * @param allCities
	 * @param allCouncillor
	 * @param bonus
	 * @param nobility
	 * @param politicsCardDeck
	 */
	public Board(Set<City> allCities, Collection<Councillor> allCouncillors, Collection<PermitTile> allPermitTiles, BonusTiles bonusTiles, NobilityTrack nobility, PoliticsCardDeck politicsCardDeck, City kingCity ){
		
		cities = new HashSet<>();
		cities.addAll(allCities);
		
		availableCouncillors = new ArrayList<>();
		
		//cities shunting
		
		Set<City> coastCities = new HashSet<>();
		Set<City> hillCities = new HashSet<>();
		Set<City> mountainCities = new HashSet<>();
		
		shuntCities(allCities, coastCities, hillCities, mountainCities);
		
		//councillors shunting
		
		Collection<Councillor> coastCouncillors = new ArrayList<>();
		Collection<Councillor> hillCouncillors = new ArrayList<>();
		Collection<Councillor> mountainCouncillors = new ArrayList<>();
		Collection<Councillor> kingCouncillors = new ArrayList<>();
		
		shuntCouncillors(allCouncillors, coastCouncillors, hillCouncillors, mountainCouncillors, kingCouncillors);
		
		//Permit tiles shunting
		
		Collection<PermitTile> coastPermitTiles = new ArrayList<>();
		Collection<PermitTile> hillPermitTiles = new ArrayList<>();
		Collection<PermitTile> mountainPermitTiles = new ArrayList<>();
		
		
		shuntPermitTiles(allPermitTiles, coastPermitTiles, hillPermitTiles, mountainPermitTiles, coastCities, hillCities);
		
		coastRegion = new Region("Coast", coastCities, bonusTiles.getCoastBonus(), coastCouncillors, coastPermitTiles);
		hillRegion = new Region("Hill", hillCities, bonusTiles.getHillBonus(), hillCouncillors, hillPermitTiles);
		mountainRegion = new Region("Mountain", mountainCities, bonusTiles.getMountainBonus(), mountainCouncillors, mountainPermitTiles);
		
		king = new King(kingCity);
		
		kingCouncil = new CouncillorBalcony(kingCouncillors);
		
		kingRewardTile = bonusTiles.getKingBonus();
		
		nobilityTrack = nobility;
		
		this.politicsCardDeck = politicsCardDeck;
		
	}
	
	/**
	 * Splits the Set of all the cities of the game among the 3 regions (Coast, Hill, Mountin) according to the convention that the 
	 * possible excess of cities is set in the 3rd region (Mountain).
	 * 
	 * @param all
	 * @param region1
	 * @param region2
	 * @param region3
	 */
	public void shuntCities(Set<City> all, Set<City> region1, Set<City> region2, Set<City> region3){
		
		if(all.size()<3) 
			throw new IllegalArgumentException();
		else {
			
			Iterator<City> it = all.iterator();
			int numberOfCoastCities = all.size()/3;
			int numberOfHillCities = (all.size() - numberOfCoastCities)/2;
			int numberOfMountainCities = all.size() - numberOfCoastCities - numberOfHillCities;
		
			int i=0;
			while(it.hasNext()){
			
				if(i<numberOfCoastCities) 									// Checks what region to add the cities in.
					region1.add(it.next());								
				else
					if(i<(numberOfCoastCities + numberOfHillCities)) 
						region2.add(it.next());
					else
						region3.add(it.next());
			
				i++;
			
			}
			
			if(region3.size() != numberOfMountainCities)
				throw new UnexpectedResultException();
			
		}
		
	}
	
	/**
	 * Splits all the Councillors among the 4 balconies of the board (Coast balcony, Hill balcony, Mountain balcony, King balcony).
	 * The shunting operation is based on the rules of the real game, according to which on every balcony must sit 4 councillors.
	 * 
	 * @param all
	 * @param balcony1
	 * @param balcony2
	 * @param balcony3
	 * @param balcony4
	 */
	public void shuntCouncillors(Collection<Councillor> all, Collection<Councillor> balcony1, 
			Collection<Councillor> balcony2, Collection<Councillor> balcony3, Collection<Councillor> balcony4) {
		
		if(all.size()<16) 
			throw new IllegalArgumentException();
		else{
			
			int numberOfFreeCouncillors = all.size() - (COUNCILLORS_PER_BALCONY * 4);
			Iterator<Councillor> it = all.iterator();
			int i=0;
			while(it.hasNext()){
				
				if(i<4) 
					balcony1.add(it.next());
				else
					if(i<8) 
						balcony2.add(it.next());
					else
						if(i<12) 
							balcony3.add(it.next());
						else
							if(i<16) 
								balcony4.add(it.next());
							else availableCouncillors.add(it.next());
						
				i++;
				
			}
			
			if(availableCouncillors.size() != numberOfFreeCouncillors)
				throw new UnexpectedResultException();
			
		}
		
	}
	
	/**
	 * Splits all the permit tiles according to the cities they allow to build on (e.g. permit tiles with a city of the Coast is added
	 * to the Coast deck).
	 * 
	 * @param all
	 * @param deck1
	 * @param deck2
	 * @param deck3
	 * @param region1
	 * @param region2
	 */
	public void shuntPermitTiles(Collection<PermitTile> all, Collection<PermitTile> deck1, Collection<PermitTile> deck2, Collection<PermitTile> deck3, 
			Collection<City> region1, Collection<City> region2){
		
		if(all.size()<6) 
			throw new IllegalArgumentException(); // It is required to have at least 2 visible permit tiles per region.
		else{
			
			Iterator<PermitTile> it = all.iterator();
			
			while(it.hasNext()){
				
				Set<City> tileCities = new HashSet<>();
				tileCities.addAll(it.next().getCitySet());
				
				Iterator<City> itc = tileCities.iterator();
				
				if(region1.contains(itc.next())) 
					deck1.add(it.next());									// It is sufficient to check wether the first city belongs to a 
																						// region. Permit tiles have to be consistent.
				else
					if(region2.contains(itc.next())) 
						deck2.add(it.next());
					else
						deck3.add(it.next());
				
			}
			
		}
		
	}
	
	//getter methods
	
	/**
	 * 
	 * @return
	 */
	public Set<City> getCities() {
		
		return cities;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Region getCoastRegion() {
		
		return coastRegion;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Region getHillRegion() {
		
		return hillRegion;
		
	}

	/**
	 * 
	 * @return
	 */
	public Region getMountainRegion() {
		
		return mountainRegion;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public King getKing() {
		
		return king;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public CouncillorBalcony getKingCouncil() {
		
		return kingCouncil;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Bonus getKingRewardTile() {
		
		return kingRewardTile;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public NobilityTrack getNobilityTrack() {
		
		return nobilityTrack;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public PoliticsCardDeck getPoliticsCardDeck() {
		
		return politicsCardDeck;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Collection<Councillor> getAvailableCouncillors() {
		
		return availableCouncillors;
		
	}
	
}
