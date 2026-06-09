import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import LoginScreen from '../screens/LoginScreen';
import RegisterScreen from '../screens/RegisterScreen';
import HomeScreen from '../screens/HomeScreen';
import LocationsScreen from '../screens/LocationsScreen';
import LocationDetailScreen from '../screens/LocationDetailScreen';
import AddLocationScreen from '../screens/AddLocationScreen';
import HistoryScreen from '../screens/HistoryScreen';

export type RootStackParamList = {
  Login: undefined;
  Register: undefined;
  Home: undefined;
  Locations: undefined;
  LocationDetail: { id: number };
  AddLocation: undefined;
  History: undefined;
};

const Stack = createNativeStackNavigator<RootStackParamList>();

/**
 * Define a pilha de navegação do aplicativo. Cada tela corresponde a
 * uma view renderizada pelo React Navigation. Ajuste as opções de
 * cabeçalho ou adicione novas telas conforme necessário.
 */
export default function AppNavigator() {
  return (
    <Stack.Navigator>
      <Stack.Screen name="Login" component={LoginScreen} options={{ headerShown: false }} />
      <Stack.Screen name="Register" component={RegisterScreen} options={{ title: 'Registrar' }} />
      <Stack.Screen name="Home" component={HomeScreen} options={{ title: 'Dashboard' }} />
      <Stack.Screen name="Locations" component={LocationsScreen} options={{ title: 'Locais' }} />
      <Stack.Screen name="LocationDetail" component={LocationDetailScreen} options={{ title: 'Detalhe do Local' }} />
      <Stack.Screen name="AddLocation" component={AddLocationScreen} options={{ title: 'Adicionar Local' }} />
      <Stack.Screen name="History" component={HistoryScreen} options={{ title: 'Histórico' }} />
    </Stack.Navigator>
  );
}
