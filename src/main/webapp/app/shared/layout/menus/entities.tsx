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
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
