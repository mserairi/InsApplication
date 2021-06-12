import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/enfant">
      <Translate contentKey="global.menu.entities.enfant" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/category">
      <Translate contentKey="global.menu.entities.category" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/inscription">
      <Translate contentKey="global.menu.entities.inscription" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/sous-category">
      <Translate contentKey="global.menu.entities.sousCategory" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/lasession">
      <Translate contentKey="global.menu.entities.lasession" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/commande-inscriptions">
      <Translate contentKey="global.menu.entities.commandeInscriptions" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/type-remise">
      <Translate contentKey="global.menu.entities.typeRemise" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/remise">
      <Translate contentKey="global.menu.entities.remise" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/facture">
      <Translate contentKey="global.menu.entities.facture" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/paiement">
      <Translate contentKey="global.menu.entities.paiement" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/cours">
      <Translate contentKey="global.menu.entities.cours" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/groupe">
      <Translate contentKey="global.menu.entities.groupe" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/creneau">
      <Translate contentKey="global.menu.entities.creneau" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/salle">
      <Translate contentKey="global.menu.entities.salle" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/formation">
      <Translate contentKey="global.menu.entities.formation" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/user-extras">
      <Translate contentKey="global.menu.entities.userExtras" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
