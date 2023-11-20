package com.project.adress.property;

import com.project.adress.exception.DuplicateResourceException;
import com.project.adress.exception.RequestValidationException;
import com.project.adress.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {

    @Mock
    private PropertyDao propertyDao;
    PropertyService underTest;

    @BeforeEach
    void setUp() {
        underTest=new PropertyService(propertyDao);
    }

    @Test
    void getAllProperties() {

        //Given
        Integer streetId=1000;

        //When
        underTest.getAllProperties(streetId);

        //Then
        verify(propertyDao).selectAllProperties(streetId);
    }

    @Test
    void canGetProperty() {

        //Given
        Integer streetId=1000;
        int id=10000;
        Property Property = new Property(id,
                "Test1",
                streetId);
        when(propertyDao.selectPropertyById(id)).thenReturn(Optional.of(Property));

        //When
        Property actual = underTest.getProperty(id);

        //Then
        assertThat(actual).isEqualTo(Property);

    }

    @Test
    void willThrowWhenNotFound(){
        //Given
        int id = -1;

        when(propertyDao.selectPropertyById(id)).thenReturn(Optional.empty());

        //When

        //Then
        assertThatThrownBy(() -> underTest.getProperty(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("property with id [%s] not found".formatted(id));

    }

    @Test
    void willThrowWhenForeignKeyNotFound(){
        //Given
        Integer streetId=1000;

        when(propertyDao.existStreetWithStreetId(streetId)).thenReturn(false);
        PropertyRegistrationRequest PropertyRegistrationRequest= new PropertyRegistrationRequest(20000,"Test2",1000);
        Property Property = new Property(
                20000,
                "Test2",
                streetId);
        //When
        assertThatThrownBy(() ->underTest.addProperty(PropertyRegistrationRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("street id does not exist");

        //Then
        verify(propertyDao,never()).insertProperty(Property);

    }

    @Test
    void addProperty() {

        //Given
        Integer id=30000;
        Integer streetId=1000;
        when(propertyDao.existStreetWithStreetId(streetId)).thenReturn(true);
        PropertyRegistrationRequest Property = new PropertyRegistrationRequest(id,"Test3",streetId);

        //When
        underTest.addProperty(Property);

        //Then
        ArgumentCaptor<Property> PropertyArgumentCaptor = ArgumentCaptor.forClass(
                Property.class
        );
        verify(propertyDao).insertProperty(PropertyArgumentCaptor.capture());

        Property capturedProperty = PropertyArgumentCaptor.getValue();

        assertThat(capturedProperty.getProperty_id()).isEqualTo(30000);
        assertThat(capturedProperty.getProperty_number()).isEqualTo("Test3");
        assertThat(capturedProperty.getStreet_id()).isEqualTo(streetId);

    }

    @Test
    void willThrowWhenPropertyIdExistsAlready() {

        //Given
        Integer id=40000;
        when(propertyDao.existPropertyById(id)).thenReturn(true);
        Integer streetId=1000;
        PropertyRegistrationRequest Property = new PropertyRegistrationRequest(id,"Test4",streetId);

        //When
        assertThatThrownBy(() ->underTest.addProperty(Property))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("property id already taken");

        //Then

        verify(propertyDao, never()).insertProperty(any());
    }

    @Test
    void willThrowWhenPropertyNameExistsAlready() {

        //Given
        String name ="Test5";
        when(propertyDao.existPropertyWithNumber(name)).thenReturn(true);
        Integer streetId=1000;
        PropertyRegistrationRequest Property = new PropertyRegistrationRequest(50000,name,streetId);

        //When
        assertThatThrownBy(() ->underTest.addProperty(Property))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("property name already taken");

        //Then

        verify(propertyDao, never()).insertProperty(any());
    }

    @Test
    void deleteProperty() {

        //Given
        int id = 600;

        when(propertyDao.existPropertyById(id)).thenReturn(true);

        //When
        underTest.deleteProperty(id);

        //Then
        verify(propertyDao).deletePropertyById(id);

    }

    @Test
    void willThrowWhenDeletePropertyIdNotExists() {

        //Given
        int id = -1;

        when(propertyDao.existPropertyById(id)).thenReturn(false);

        //When

        assertThatThrownBy(() -> underTest.deleteProperty(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("property with id [%s] not found".formatted(id));

        //Then
        verify(propertyDao,never()).deletePropertyById(id);
    }

    @Test
    void canUpdateAllPropertyProperties() {

        //Given
        int id = 70000;
        Property Property = new Property(id,"Test7",1000);
        when(propertyDao.selectPropertyById(id)).thenReturn(Optional.of(Property));

        String newName = "Updated1";
        Integer newStreetId=2000;
        when(propertyDao.existStreetWithStreetId(newStreetId)).thenReturn(true);

        PropertyUpdateRequest update = new PropertyUpdateRequest(newName,newStreetId);

        when(propertyDao.existPropertyWithNumber(newName)).thenReturn(false);

        //When
        underTest.updateProperty(id,update);

        //Then
        ArgumentCaptor<Property> PropertyArgumentCaptor = ArgumentCaptor.forClass(Property.class);

        verify(propertyDao).updateProperty(PropertyArgumentCaptor.capture());
        Property capturedProperty = PropertyArgumentCaptor.getValue();

        assertThat(capturedProperty.getProperty_number()).isEqualTo(update.property_number());
        assertThat(capturedProperty.getStreet_id()).isEqualTo(update.street_id());

    }

    @Test
    void canOnlyUpdatePropertyName() {

        //Given
        int id = 80000;
        Property Property = new Property(id,"Test8",1000);
        when(propertyDao.selectPropertyById(id)).thenReturn(Optional.of(Property));

        String newName = "Updated2";

        PropertyUpdateRequest update = new PropertyUpdateRequest(newName,null);

        when(propertyDao.existPropertyWithNumber(newName)).thenReturn(false);

        //When
        underTest.updateProperty(id,update);

        //Then
        ArgumentCaptor<Property> PropertyArgumentCaptor = ArgumentCaptor.forClass(Property.class);

        verify(propertyDao).updateProperty(PropertyArgumentCaptor.capture());
        Property capturedProperty = PropertyArgumentCaptor.getValue();

        assertThat(capturedProperty.getProperty_number()).isEqualTo(update.property_number());
        assertThat(capturedProperty.getStreet_id()).isEqualTo(Property.getStreet_id());

    }

    @Test
    void canOnlyUpdatePropertyStreetId() {

        //Given
        int id = 90000;
        Property Property = new Property(id,"Test9",1000);
        when(propertyDao.selectPropertyById(id)).thenReturn(Optional.of(Property));

        Integer newStreetId = 2000;
        when(propertyDao.existStreetWithStreetId(newStreetId)).thenReturn(true);

        PropertyUpdateRequest update = new PropertyUpdateRequest(null,newStreetId);

        //When
        underTest.updateProperty(id,update);

        //Then
        ArgumentCaptor<Property> PropertyArgumentCaptor = ArgumentCaptor.forClass(Property.class);

        verify(propertyDao).updateProperty(PropertyArgumentCaptor.capture());
        Property capturedProperty = PropertyArgumentCaptor.getValue();

        assertThat(capturedProperty.getProperty_number()).isEqualTo(Property.getProperty_number());
        assertThat(capturedProperty.getStreet_id()).isEqualTo(update.street_id());

    }

    @Test
    void willThrowUpdateWhenPropertyNameAlreadyTaken(){
        //Given
        int id = 100000;
        Property Property = new Property(id,"Test10",1000);
        when(propertyDao.selectPropertyById(id)).thenReturn(Optional.of(Property));

        String newName = "Updated3";
        when(propertyDao.existPropertyWithNumber(newName)).thenReturn(true);

        PropertyUpdateRequest update = new PropertyUpdateRequest(newName,null);

        //When
        assertThatThrownBy(()->underTest.updateProperty(id,update))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("property name already taken");

        //Then
        verify(propertyDao,never()).updateProperty(any());

    }

    @Test
    void willThrowUpdateWhenStreetIdNotExists(){
        //Given
        int id = 110000;

        Property Property = new Property(id,"Test11",1000);
        when(propertyDao.selectPropertyById(id)).thenReturn(Optional.of(Property));

        Integer newStreetId = -1;
        when(propertyDao.existStreetWithStreetId(newStreetId)).thenReturn(false);

        PropertyUpdateRequest update = new PropertyUpdateRequest(null,newStreetId);

        //When
        assertThatThrownBy(()->underTest.updateProperty(id,update))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("street with id [%s] not found");

        //Then
        verify(propertyDao,never()).updateProperty(any());

    }

    @Test
    void willThrowUpdateWhenNoChanges(){
        //Given
        int id = 120000;

        Property Property = new Property(id,"Test12",1000);
        when(propertyDao.selectPropertyById(id)).thenReturn(Optional.of(Property));

        PropertyUpdateRequest update = new PropertyUpdateRequest(Property.getProperty_number(),Property.getStreet_id());

        //When
        assertThatThrownBy(()->underTest.updateProperty(id,update))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");

        //Then
        verify(propertyDao,never()).updateProperty(any());

    }
}
