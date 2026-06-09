import React, { useEffect, useState, useCallback } from 'react';
import { useFocusEffect } from '@react-navigation/native';
import { View, Text, FlatList, TouchableOpacity, Button, StyleSheet } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { RootStackParamList } from '../navigation/AppNavigator';
import api from '../services/api';

type Props = NativeStackScreenProps<RootStackParamList, 'Locations'>;

/**
 * Tela que lista todos os locais monitorados pelo usuário atual. Ao tocar
 * em um local, o app navega para a tela de detalhes. Também há um botão
 * para cadastrar novos locais.
 */
export default function LocationsScreen({ navigation }: Props) {
  const [locations, setLocations] = useState<any[]>([]);

  useEffect(() => {
    const fetchLocations = async () => {
      try {
        const res = await api.get('/locations');
        setLocations(res.data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchLocations();
  }, []);

  // Atualiza a lista sempre que a tela volta ao foco, como após cadastrar um local.
  useFocusEffect(
    useCallback(() => {
      const refresh = async () => {
        try {
          const res = await api.get('/locations');
          setLocations(res.data);
        } catch (error) {
          console.error(error);
        }
      };
      refresh();
    }, [])
  );

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Locais Monitorados</Text>
      <FlatList
        data={locations}
        keyExtractor={(item) => item.id.toString()}
        renderItem={({ item }) => (
          <TouchableOpacity onPress={() => navigation.navigate('LocationDetail', { id: item.id })}>
            <View style={styles.card}>
              <Text style={styles.cardTitle}>{item.name}</Text>
              <Text>{item.city}, {item.state}</Text>
            </View>
          </TouchableOpacity>
        )}
        ListEmptyComponent={<Text>Nenhum local cadastrado.</Text>}
      />
      <Button title="Adicionar Local" onPress={() => navigation.navigate('AddLocation')} />
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
  card: {
    padding: 15,
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 4,
    marginBottom: 10
  },
  cardTitle: {
    fontSize: 18,
    fontWeight: 'bold'
  }
});
