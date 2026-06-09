import React, { useState } from 'react';
import { View, Text, TextInput, Button, StyleSheet, Alert } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import api from '../services/api';
import { useTheme } from '../theme/ThemeContext';

type Props = NativeStackScreenProps<RootStackParamList, 'AddLocation'>;

/**
 * Tela para criar um novo local monitorado. O usuário informa nome,
 * cidade, estado e coordenadas. Ao salvar, uma requisição POST é enviada
 * ao backend e a tela fecha, retornando para a lista de locais. Entradas
 * numéricas inválidas são tratadas antes do envio.
 */
export default function AddLocationScreen({ navigation }: Props) {
  const [name, setName] = useState('');
  const [city, setCity] = useState('');
  const [state, setState] = useState('');
  const [latitude, setLatitude] = useState('');
  const [longitude, setLongitude] = useState('');
  const { colors } = useTheme();

  const handleSave = async () => {
    const latNum = parseFloat(latitude);
    const lonNum = parseFloat(longitude);
    if (isNaN(latNum) || isNaN(lonNum)) {
      Alert.alert('Erro', 'Latitude e longitude devem ser números válidos.');
      return;
    }
    try {
      const body = {
        name,
        city,
        state,
        latitude: latNum,
        longitude: lonNum
      };
      await api.post('/locations', body);
      navigation.goBack();
    } catch (error) {
      console.error(error);
      Alert.alert('Erro', 'Não foi possível salvar o local.');
    }
  };

  return (
    <View style={[styles.container, { backgroundColor: colors.background }]}>
      <Text style={[styles.title, { color: colors.text }]}>Novo Local</Text>
      <TextInput
        placeholder="Nome"
        placeholderTextColor={colors.placeholder}
        value={name}
        onChangeText={setName}
        style={[
          styles.input,
          { backgroundColor: colors.input, borderColor: colors.border, color: colors.text },
        ]}
      />
      <TextInput
        placeholder="Cidade"
        placeholderTextColor={colors.placeholder}
        value={city}
        onChangeText={setCity}
        style={[
          styles.input,
          { backgroundColor: colors.input, borderColor: colors.border, color: colors.text },
        ]}
      />
      <TextInput
        placeholder="Estado"
        placeholderTextColor={colors.placeholder}
        value={state}
        onChangeText={setState}
        style={[
          styles.input,
          { backgroundColor: colors.input, borderColor: colors.border, color: colors.text },
        ]}
      />
      <TextInput
        placeholder="Latitude"
        placeholderTextColor={colors.placeholder}
        value={latitude}
        onChangeText={setLatitude}
        keyboardType="numeric"
        style={[
          styles.input,
          { backgroundColor: colors.input, borderColor: colors.border, color: colors.text },
        ]}
      />
      <TextInput
        placeholder="Longitude"
        placeholderTextColor={colors.placeholder}
        value={longitude}
        onChangeText={setLongitude}
        keyboardType="numeric"
        style={[
          styles.input,
          { backgroundColor: colors.input, borderColor: colors.border, color: colors.text },
        ]}
      />
      <Button title="Salvar" onPress={handleSave} color={colors.primary} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#fff'
  },
  title: {
    fontSize: 24,
    marginBottom: 20
  },
  input: {
    borderWidth: 1,
    borderColor: '#ccc',
    padding: 10,
    marginBottom: 10,
    borderRadius: 4
  }
});
