package Theresa.card.attack;

import Theresa.action.TheresaAttackAction;
import Theresa.card.AbstractTheresaCard;
import Theresa.modifier.EternalMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class UnforgivableSin extends AbstractTheresaCard {
    public static final String ID = "theresa:UnforgivableSin";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public UnforgivableSin(int upgrades) {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        baseDamage = damage = 7;
        baseMagicNumber = magicNumber = 1;
        cardsToPreview = new SarkazSee();
        CardModifierManager.addModifier(this,new EternalMod());
    }

    public UnforgivableSin() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new TheresaAttackAction(true));
        for(int i = 0; i < this.magicNumber; i++) {
            int randomNumber = AbstractDungeon.cardRandomRng.random(0, this.damage*2);
            addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, randomNumber, damageTypeForTurn), skillEffect));
        }
    }

    @Override
    public void onRemoveFromMasterDeck() {
        AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new SarkazSee(), Settings.WIDTH/2F, Settings.HEIGHT/2F));
    }

    @Override
    public void upgrade() {
        if(timesUpgraded<3){
            upgradeDamage(2);
            upgradeMagicNumber(1);
            ++this.timesUpgraded;
            this.upgraded = true;
            this.name = cardStrings.NAME + "+" + this.timesUpgraded;
            this.initializeTitle();
        }
    }

    public boolean canUpgrade() {
        return timesUpgraded<3;
    }

    @Override
    public AbstractCard makeCopy() {
        return new UnforgivableSin(timesUpgraded);
    }
}


